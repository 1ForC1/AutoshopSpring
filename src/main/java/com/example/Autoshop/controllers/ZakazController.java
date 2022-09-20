package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.Service;
import com.example.Autoshop.models.User;
import com.example.Autoshop.models.Zakaz;
import com.example.Autoshop.repo.AutoshopRepository;
import com.example.Autoshop.repo.UserRepository;
import com.example.Autoshop.repo.ZakazRepository;
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
public class ZakazController {
    @Autowired
    private ZakazRepository zakazRepository;

    @Autowired
    private AutoshopRepository autoshopRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/zakaz")
    public String zakazMain(Model model){
        Iterable<Zakaz> zakazy = zakazRepository.findAll();
        model.addAttribute("zakaz", zakazy);
        return "zakaz/zakaz-main";
    }

    @GetMapping("/zakaz/add")
    public String zakazAdd(Service service, Model model) {
        Iterable<Autoshop> autoshops = autoshopRepository.findAll();
        model.addAttribute("autoshop", autoshops);
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        return "zakaz/zakaz-add";
    }

    @PostMapping("/zakaz/add")
    public String zakazNewAdd(@ModelAttribute("zakaz") @Valid Zakaz zakaz, @RequestParam String title, @RequestParam String username, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "zakaz/zakaz-add";
        }
        Autoshop autoshop = autoshopRepository.findAutoshopByTitle(title);
        User user = userRepository.findByUsername(username);
        zakaz = new Zakaz(user, autoshop);
        zakazRepository.save(zakaz);
        return "redirect:/zakaz";
    }

    @GetMapping("/zakaz/filter")
    public String zakazFilter(Model model){ return "zakaz/zakaz-filter"; }

    @PostMapping("/zakaz/filter/result")
    public String zakazResult(@RequestParam String title, Model model)
    {
        List<Zakaz> result = zakazRepository.findZakazByAutoshopTitle(title);
        model.addAttribute("result", result);
        return "zakaz/zakaz-filter";
    }

    @GetMapping("/zakaz/{id}")
    public String zakazDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Zakaz> zakaz = zakazRepository.findById(id);
        ArrayList<Zakaz> res = new ArrayList<>();
        zakaz.ifPresent(res::add);
        model.addAttribute("zakaz", res);
        if(!zakazRepository.existsById(id))
        {
            return "redirect:/zakaz";
        }
        return "/zakaz/zakaz-details";
    }

    @GetMapping("/zakaz/{id}/edit")
    public String zakazEdit(@PathVariable("id")long id, Model model)
    {
        Iterable<Autoshop> autoshops = autoshopRepository.findAll();
        model.addAttribute("autoshop", autoshops);
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        Zakaz res = zakazRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("zakaz",res);
        return "zakaz/zakaz-edit";
    }

    @PostMapping("/zakaz/{id}/edit")
    public String zakazUpdate(@PathVariable("id")long id, @ModelAttribute("zakaz")@Valid Zakaz zakaz, @RequestParam String title, @RequestParam String username, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "zakaz/zakaz-edit";
        }
        Autoshop autoshop = autoshopRepository.findAutoshopByTitle(title);
        User user = userRepository.findByUsername(username);
        zakaz = new Zakaz(user, autoshop);
        zakaz.setId(id);
        zakazRepository.save(zakaz);
        return "redirect:/zakaz";
    }

    @PostMapping("/zakaz/{id}/remove")
    public String zakazDelete(@PathVariable("id") long id, Model model){
        try {
            Zakaz zakaz = zakazRepository.findById(id).orElseThrow();
            zakazRepository.delete(zakaz);
        }catch (Exception ignored){}
        return "redirect:/zakaz";
    }
}
