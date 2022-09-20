package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.Service;
import com.example.Autoshop.models.User;
import com.example.Autoshop.repo.ServiceRepository;
import com.example.Autoshop.repo.UserRepository;
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
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/service")
    public String serviceMain(Model model){
        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("service", services);
        return "service/service-main";
    }

    @GetMapping("/service/add")
    public String serviceAdd(Service service, Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        return "service/service-add";
    }

    @PostMapping("/service/add")
    public String serviceNewAdd(@ModelAttribute("service") @Valid Service service, @RequestParam String username, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "service/service-add";
        }
        User user = userRepository.findByUsername(username);
        service = new Service(service.getTitle(), service.getCost(), user);
        serviceRepository.save(service);
        return "redirect:/service";
    }

    @GetMapping("/service/filter")
    public String serviceFilter(Model model){ return "service/service-filter"; }

    @PostMapping("/service/filter/result")
    public String serviceResult(@RequestParam String title, Model model)
    {
        List<Service> result = serviceRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "service/service-filter";
    }

    @GetMapping("/service/{id}")
    public String serviceDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Service> service = serviceRepository.findById(id);
        ArrayList<Service> res = new ArrayList<>();
        service.ifPresent(res::add);
        model.addAttribute("service", res);
        if(!serviceRepository.existsById(id))
        {
            return "redirect:/service";
        }
        return "/service/service-details";
    }

    @GetMapping("/service/{id}/edit")
    public String serviceEdit(@PathVariable("id")long id, Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        Service res = serviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("service",res);
        return "service/service-edit";
    }

    @PostMapping("/service/{id}/edit")
    public String serviceUpdate(@PathVariable("id")long id, @ModelAttribute("service")@Valid Service service, @RequestParam String username, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "service/service-edit";
        }
        User user = userRepository.findByUsername(username);
        service = new Service(service.getTitle(), service.getCost(), user);
        service.setId(id);
        serviceRepository.save(service);
        return "redirect:/service";
    }

    @PostMapping("/service/{id}/remove")
    public String serviceDelete(@PathVariable("id") long id, Model model){
        try{
        Service service = serviceRepository.findById(id).orElseThrow();
        serviceRepository.delete(service);
        }catch (Exception ignored){}
        return "redirect:/service";
    }
}
