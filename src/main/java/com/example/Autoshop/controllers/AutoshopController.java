package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.User;
import com.example.Autoshop.repo.AutoshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AutoshopController {
    @Autowired
    private AutoshopRepository autoshopRepository;

    @GetMapping("/autoshop")
    public String autoshopMain(Model model){
        Iterable<Autoshop> autoshops = autoshopRepository.findAll();
        model.addAttribute("autoshop", autoshops);
        return "autoshop/autoshop-main";
    }

    @GetMapping("/autoshop/add")
    public String autoshopAdd(Autoshop autoshop, Model model) {
        return "autoshop/autoshop-add";
    }

    @PostMapping("/autoshop/add")
    public String autoshopNewAdd(@ModelAttribute("autoshop") @Valid Autoshop autoshop, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "autoshop/autoshop-add";
        }
        autoshop = new Autoshop(autoshop.getTitle(), autoshop.getAddress());
        autoshopRepository.save(autoshop);
        return "redirect:/autoshop";
    }

    @GetMapping("/autoshop/filter")
    public String autoshopFilter(Model model){ return "autoshop/autoshop-filter"; }

    @PostMapping("/autoshop/filter/result")
    public String autoshopResult(@RequestParam String title, Model model)
    {
        List<Autoshop> result = autoshopRepository.findAutoshopByTitleContains(title);
        model.addAttribute("result", result);
        return "autoshop/autoshop-filter";
    }

    @GetMapping("/autoshop/{id}")
    public String autoshopDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Autoshop> autoshop = autoshopRepository.findById(id);
        ArrayList<Autoshop> res = new ArrayList<>();
        autoshop.ifPresent(res::add);
        model.addAttribute("autoshop", res);
        if(!autoshopRepository.existsById(id))
        {
            return "redirect:/autoshop";
        }
        return "/autoshop/autoshop-details";
    }

    @GetMapping("/autoshop/{id}/edit")
    public String autoshopEdit(@PathVariable("id")long id, Model model)
    {
        Autoshop res = autoshopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("autoshop",res);
        return "autoshop/autoshop-edit";
    }

    @PostMapping("/autoshop/{id}/edit")
    public String autoshopUpdate(@PathVariable("id")long id, @ModelAttribute("autoshop")@Valid Autoshop autoshop, BindingResult bindingResult)
    {
        autoshop.setId(id);
        if(bindingResult.hasErrors())
        {
            return "autoshop/autoshop-edit";
        }
        autoshopRepository.save(autoshop);
        return "redirect:/autoshop";
    }

    @PostMapping("/autoshop/{id}/remove")
    public String autoshopDelete(@PathVariable("id") long id, Model model){
        Autoshop autoshop = autoshopRepository.findById(id).orElseThrow();
        autoshopRepository.delete(autoshop);
        return "redirect:/autoshop";
    }
}
