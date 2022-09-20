package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.User;
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
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String userMain(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        return "user/user-main";
    }

    @GetMapping("/user/add")
    public String userAdd(User user, Model model) {
        return "user/user-add";
    }

    @PostMapping("/user/add")
    public String userNewAdd(@ModelAttribute("user") @Valid User user, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "user/user-add";
        }
        user = new User(user.getSurname(), user.getName(), user.getPatronymic(), user.getUsername(), user.getPassword(), false);
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/user/filter")
    public String userFilter(Model model){ return "user/user-filter"; }

    @PostMapping("/user/filter/result")
    public String userResult(@RequestParam String surname, Model model)
    {
        List<User> result = userRepository.findBySurnameContains(surname);
        model.addAttribute("result", result);
        return "user/user-filter";
    }

    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        if(!userRepository.existsById(id))
        {
            return "redirect:/user";
        }
        return "/user/user-details";
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable("id")long id, Model model)
    {
        User res = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("user",res);
        return "user/user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String userUpdate(@PathVariable("id")long id, @ModelAttribute("user")@Valid User user, BindingResult bindingResult)
    {
        user.setId(id);
        if(bindingResult.hasErrors())
        {
            return "user/user-edit";
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/user/{id}/remove")
    public String userDelete(@PathVariable("id") long id, Model model){
        try{
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        }catch (Exception ignored){}
        return "redirect:/user";
    }
}
