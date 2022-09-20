package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.CarBrand;
import com.example.Autoshop.repo.CarBrandRepository;
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
public class CarBrandController {
    @Autowired
    private CarBrandRepository carBrandRepository;

    @GetMapping("/carBrand")
    public String carBrandMain(Model model){
        Iterable<CarBrand> carBrands = carBrandRepository.findAll();
        model.addAttribute("carBrand", carBrands);
        return "carBrand/carBrand-main";
    }

    @GetMapping("/carBrand/add")
    public String carBrandAdd(CarBrand carBrand, Model model) {
        return "carBrand/carBrand-add";
    }

    @PostMapping("/carBrand/add")
    public String carBrandNewAdd(@ModelAttribute("carBrand") @Valid CarBrand carBrand, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "carBrand/carBrand-add";
        }
        carBrand = new CarBrand(carBrand.getTitle());
        carBrandRepository.save(carBrand);
        return "redirect:/carBrand";
    }

    @GetMapping("/carBrand/filter")
    public String carBrandFilter(Model model){ return "carBrand/carBrand-filter"; }

    @PostMapping("/carBrand/filter/result")
    public String carBrandResult(@RequestParam String title, Model model)
    {
        List<CarBrand> result = carBrandRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "carBrand/carBrand-filter";
    }

    @GetMapping("/carBrand/{id}")
    public String carBrandDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<CarBrand> carBrand = carBrandRepository.findById(id);
        ArrayList<CarBrand> res = new ArrayList<>();
        carBrand.ifPresent(res::add);
        model.addAttribute("carBrand", res);
        if(!carBrandRepository.existsById(id))
        {
            return "redirect:/carBrand";
        }
        return "/carBrand/carBrand-details";
    }

    @GetMapping("/carBrand/{id}/edit")
    public String carBrandEdit(@PathVariable("id")long id, Model model)
    {
        CarBrand res = carBrandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("carBrand",res);
        return "carBrand/carBrand-edit";
    }

    @PostMapping("/carBrand/{id}/edit")
    public String carBrandUpdate(@PathVariable("id")long id, @ModelAttribute("carBrand")@Valid CarBrand carBrand, BindingResult bindingResult)
    {
        carBrand.setId(id);
        if(bindingResult.hasErrors())
        {
            return "carBrand/carBrand-edit";
        }
        carBrandRepository.save(carBrand);
        return "redirect:/carBrand";
    }

    @PostMapping("/carBrand/{id}/remove")
    public String carBrandDelete(@PathVariable("id") long id, Model model){
        try{
        CarBrand carBrand = carBrandRepository.findById(id).orElseThrow();
        carBrandRepository.delete(carBrand);
        }catch (Exception ignored){}
        return "redirect:/carBrand";
    }
}
