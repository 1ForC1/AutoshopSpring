package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.Brand;
import com.example.Autoshop.models.User;
import com.example.Autoshop.repo.BrandRepository;
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
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/brand")
    public String brandMain(Model model){
        Iterable<Brand> brands = brandRepository.findAll();
        model.addAttribute("brand", brands);
        return "brand/brand-main";
    }

    @GetMapping("/brand/add")
    public String brandAdd(Brand brand, Model model) {
        return "brand/brand-add";
    }

    @PostMapping("/brand/add")
    public String brandNewAdd(@ModelAttribute("brand") @Valid Brand brand, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "brand/brand-add";
        }
        brand = new Brand(brand.getTitle());
        brandRepository.save(brand);
        return "redirect:/brand";
    }

    @GetMapping("/brand/filter")
    public String brandFilter(Model model){ return "brand/brand-filter"; }

    @PostMapping("/brand/filter/result")
    public String brandResult(@RequestParam String title, Model model)
    {
        List<Brand> result = brandRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "brand/brand-filter";
    }

    @GetMapping("/brand/{id}")
    public String brandDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Brand> brand = brandRepository.findById(id);
        ArrayList<Brand> res = new ArrayList<>();
        brand.ifPresent(res::add);
        model.addAttribute("brand", res);
        if(!brandRepository.existsById(id))
        {
            return "redirect:/brand";
        }
        return "/brand/brand-details";
    }

    @GetMapping("/brand/{id}/edit")
    public String brandEdit(@PathVariable("id")long id, Model model)
    {
        Brand res = brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("brand",res);
        return "brand/brand-edit";
    }

    @PostMapping("/brand/{id}/edit")
    public String brandUpdate(@PathVariable("id")long id, @ModelAttribute("brand")@Valid Brand brand, BindingResult bindingResult)
    {
        brand.setId(id);
        if(bindingResult.hasErrors())
        {
            return "brand/brand-edit";
        }
        brandRepository.save(brand);
        return "redirect:/brand";
    }

    @PostMapping("/brand/{id}/remove")
    public String brandDelete(@PathVariable("id") long id, Model model){
        Brand brand = brandRepository.findById(id).orElseThrow();
        brandRepository.delete(brand);
        return "redirect:/brand";
    }
}
