package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.SparePartType;
import com.example.Autoshop.repo.SparePartTypeRepository;
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
public class SparePartTypeController {
    @Autowired
    private SparePartTypeRepository sparePartTypeRepository;

    @GetMapping("/sparePartType")
    public String sparePartTypeMain(Model model){
        Iterable<SparePartType> sparePartTypes = sparePartTypeRepository.findAll();
        model.addAttribute("sparePartType", sparePartTypes);
        return "sparePartType/sparePartType-main";
    }

    @GetMapping("/sparePartType/add")
    public String sparePartTypeAdd(SparePartType sparePartType, Model model) {
        return "sparePartType/sparePartType-add";
    }

    @PostMapping("/sparePartType/add")
    public String sparePartTypeNewAdd(@ModelAttribute("sparePartType") @Valid SparePartType sparePartType, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "sparePartType/sparePartType-add";
        }
        sparePartType = new SparePartType(sparePartType.getType());
        sparePartTypeRepository.save(sparePartType);
        return "redirect:/sparePartType";
    }

    @GetMapping("/sparePartType/filter")
    public String sparePartTypeFilter(Model model){ return "sparePartType/sparePartType-filter"; }

    @PostMapping("/sparePartType/filter/result")
    public String sparePartTypeResult(@RequestParam String type, Model model)
    {
        List<SparePartType> result = sparePartTypeRepository.findSparePartTypeByTypeContains(type);
        model.addAttribute("result", result);
        return "sparePartType/sparePartType-filter";
    }

    @GetMapping("/sparePartType/{id}")
    public String sparePartTypeDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<SparePartType> sparePartType = sparePartTypeRepository.findById(id);
        ArrayList<SparePartType> res = new ArrayList<>();
        sparePartType.ifPresent(res::add);
        model.addAttribute("sparePartType", res);
        if(!sparePartTypeRepository.existsById(id))
        {
            return "redirect:/sparePartType";
        }
        return "/sparePartType/sparePartType-details";
    }

    @GetMapping("/sparePartType/{id}/edit")
    public String sparePartTypeEdit(@PathVariable("id")long id, Model model)
    {
        SparePartType res = sparePartTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("sparePartType",res);
        return "sparePartType/sparePartType-edit";
    }

    @PostMapping("/sparePartType/{id}/edit")
    public String sparePartTypeUpdate(@PathVariable("id")long id, @ModelAttribute("sparePartType")@Valid SparePartType sparePartType, BindingResult bindingResult)
    {
        sparePartType.setId(id);
        if(bindingResult.hasErrors())
        {
            return "sparePartType/sparePartType-edit";
        }
        sparePartTypeRepository.save(sparePartType);
        return "redirect:/sparePartType";
    }

    @PostMapping("/sparePartType/{id}/remove")
    public String sparePartTypeDelete(@PathVariable("id") long id, Model model){
        try {
            SparePartType sparePartType = sparePartTypeRepository.findById(id).orElseThrow();
            sparePartTypeRepository.delete(sparePartType);
        }catch (Exception ignored){}
        return "redirect:/sparePartType";
    }
}
