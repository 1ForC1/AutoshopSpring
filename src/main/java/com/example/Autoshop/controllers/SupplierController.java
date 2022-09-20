package com.example.Autoshop.controllers;

import com.example.Autoshop.models.Autoshop;
import com.example.Autoshop.models.Supplier;
import com.example.Autoshop.repo.SupplierRepository;
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
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/supplier")
    public String supplierMain(Model model){
        Iterable<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("supplier", suppliers);
        return "supplier/supplier-main";
    }

    @GetMapping("/supplier/add")
    public String supplierAdd(Supplier supplier, Model model) {
        return "supplier/supplier-add";
    }

    @PostMapping("/supplier/add")
    public String supplierNewAdd(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "supplier/supplier-add";
        }
        supplier = new Supplier(supplier.getTitle(), supplier.getAddress());
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }

    @GetMapping("/supplier/filter")
    public String supplierFilter(Model model){ return "supplier/supplier-filter"; }

    @PostMapping("/supplier/filter/result")
    public String supplierResult(@RequestParam String title, Model model)
    {
        List<Supplier> result = supplierRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "supplier/supplier-filter";
    }

    @GetMapping("/supplier/{id}")
    public String supplierDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        ArrayList<Supplier> res = new ArrayList<>();
        supplier.ifPresent(res::add);
        model.addAttribute("supplier", res);
        if(!supplierRepository.existsById(id))
        {
            return "redirect:/supplier";
        }
        return "/supplier/supplier-details";
    }

    @GetMapping("/supplier/{id}/edit")
    public String supplierEdit(@PathVariable("id")long id, Model model)
    {
        Supplier res = supplierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("supplier",res);
        return "supplier/supplier-edit";
    }

    @PostMapping("/supplier/{id}/edit")
    public String supplierUpdate(@PathVariable("id")long id, @ModelAttribute("supplier")@Valid Supplier supplier, BindingResult bindingResult)
    {
        supplier.setId(id);
        if(bindingResult.hasErrors())
        {
            return "supplier/supplier-edit";
        }
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }

    @PostMapping("/supplier/{id}/remove")
    public String supplierDelete(@PathVariable("id") long id, Model model){
        try{
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplierRepository.delete(supplier);
        }catch (Exception ignored){}
        return "redirect:/supplier";
    }
}
