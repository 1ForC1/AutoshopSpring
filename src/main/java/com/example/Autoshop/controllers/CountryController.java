package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.Country;
import com.example.Autoshop.models.User;
import com.example.Autoshop.repo.CountryRepository;
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
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/country")
    public String countryMain(Model model){
        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("country", countries);
        return "country/country-main";
    }

    @GetMapping("/country/add")
    public String countryAdd(Country country, Model model) {
        return "country/country-add";
    }

    @PostMapping("/country/add")
    public String countryNewAdd(@ModelAttribute("country") @Valid Country country, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "country/country-add";
        }
        country = new Country(country.getTitle());
        countryRepository.save(country);
        return "redirect:/country";
    }

    @GetMapping("/country/filter")
    public String countryFilter(Model model){ return "country/country-filter"; }

    @PostMapping("/country/filter/result")
    public String countryResult(@RequestParam String title, Model model)
    {
        List<Country> result = countryRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "country/country-filter";
    }

    @GetMapping("/country/{id}")
    public String countryDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Country> country = countryRepository.findById(id);
        ArrayList<Country> res = new ArrayList<>();
        country.ifPresent(res::add);
        model.addAttribute("country", res);
        if(!countryRepository.existsById(id))
        {
            return "redirect:/country";
        }
        return "/country/country-details";
    }

    @GetMapping("/country/{id}/edit")
    public String countryEdit(@PathVariable("id")long id, Model model)
    {
        Country res = countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("country",res);
        return "country/country-edit";
    }

    @PostMapping("/country/{id}/edit")
    public String countryUpdate(@PathVariable("id")long id, @ModelAttribute("country")@Valid Country country, BindingResult bindingResult)
    {
        country.setId(id);
        if(bindingResult.hasErrors())
        {
            return "country/country-edit";
        }
        countryRepository.save(country);
        return "redirect:/country";
    }

    @PostMapping("/country/{id}/remove")
    public String countryDelete(@PathVariable("id") long id, Model model){
        try{
        Country country = countryRepository.findById(id).orElseThrow();
        countryRepository.delete(country);
        }catch (Exception ignored){}
        return "redirect:/country";
    }
}
