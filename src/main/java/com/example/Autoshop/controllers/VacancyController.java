package com.example.Autoshop.controllers;

import com.example.Autoshop.models.*;
import com.example.Autoshop.repo.AutoshopRepository;
import com.example.Autoshop.repo.ServiceRepository;
import com.example.Autoshop.repo.VacancyRepository;
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
public class VacancyController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private AutoshopRepository autoshopRepository;

    @GetMapping("/vacancy")
    public String vacancyMain(Model model){
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        model.addAttribute("vacancy", vacancies);
        return "vacancy/vacancy-main";
    }

    @GetMapping("/vacancy/add")
    public String vacancyAdd(Vacancy vacancy, Model model) {
        Iterable<Autoshop> autoshops = autoshopRepository.findAll();
        model.addAttribute("autoshop", autoshops);
        return "vacancy/vacancy-add";
    }

    @PostMapping("/vacancy/add")
    public String vacancyNewAdd(@ModelAttribute("vacancy") @Valid Vacancy vacancy, @RequestParam String title, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "vacancy/vacancy-add";
        }
        Autoshop autoshop = autoshopRepository.findAutoshopByTitle(title);
        vacancy = new Vacancy(vacancy.getPost(), vacancy.getSalary(), vacancy.getDescription(), autoshop);
        vacancyRepository.save(vacancy);
        return "redirect:/vacancy";
    }

    @GetMapping("/vacancy/filter")
    public String vacancyFilter(Model model){ return "vacancy/vacancy-filter"; }

    @PostMapping("/vacancy/filter/result")
    public String vacancyResult(@RequestParam String post, Model model)
    {
        List<Vacancy> result = vacancyRepository.findByPostContains(post);
        model.addAttribute("result", result);
        return "vacancy/vacancy-filter";
    }

    @GetMapping("/vacancy/{id}")
    public String vacancyDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        ArrayList<Vacancy> res = new ArrayList<>();
        vacancy.ifPresent(res::add);
        model.addAttribute("vacancy", res);
        if(!vacancyRepository.existsById(id))
        {
            return "redirect:/vacancy";
        }
        return "/vacancy/vacancy-details";
    }

    @GetMapping("/vacancy/{id}/edit")
    public String vacancyEdit(@PathVariable("id")long id, Model model)
    {

        Iterable<Autoshop> autoshops = autoshopRepository.findAll();
        model.addAttribute("autoshop", autoshops);
        Vacancy res = vacancyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("vacancy",res);
        return "vacancy/vacancy-edit";
    }

    @PostMapping("/vacancy/{id}/edit")
    public String vacancyUpdate(@PathVariable("id")long id, @ModelAttribute("vacancy")@Valid Vacancy vacancy,@RequestParam String title, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "vacancy/vacancy-edit";
        }
        Autoshop autoshop = autoshopRepository.findAutoshopByTitle(title);
        vacancy = new Vacancy(vacancy.getPost(), vacancy.getSalary(), vacancy.getDescription(), autoshop);
        vacancy.setId(id);
        vacancyRepository.save(vacancy);
        return "redirect:/vacancy";
    }

    @PostMapping("/vacancy/{id}/remove")
    public String vacancyDelete(@PathVariable("id") long id, Model model){
        try{
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancyRepository.delete(vacancy);
        }catch (Exception ignored){}
        return "redirect:/vacancy";
    }
}
