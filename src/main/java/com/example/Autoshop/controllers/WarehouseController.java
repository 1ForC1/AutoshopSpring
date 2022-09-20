package com.example.Autoshop.controllers;

import com.example.Autoshop.models.*;
import com.example.Autoshop.repo.SupplierRepository;
import com.example.Autoshop.repo.UserRepository;
import com.example.Autoshop.repo.WarehouseRepository;
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
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    public SupplierRepository supplierRepository;

    @GetMapping("/warehouse")
    public String warehouseMain(Model model){
        Iterable<Warehouse> warehouses = warehouseRepository.findAll();
        model.addAttribute("warehouse", warehouses);
        return "warehouse/warehouse-main";
    }

    @GetMapping("/warehouse/add")
    public String warehouseAdd(Warehouse warehouse, Model model) {
        Iterable<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("supplier", suppliers);
        return "warehouse/warehouse-add";
    }

    @PostMapping("/warehouse/add")
    public String warehouseNewAdd(@ModelAttribute("warehouse") @Valid Warehouse warehouse, @RequestParam String title, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "warehouse/warehouse-add";
        }
        Supplier supplier = supplierRepository.findByTitle(title);
        warehouse = new Warehouse(warehouse.getAddress(), supplier);
        warehouseRepository.save(warehouse);
        return "redirect:/warehouse";
    }

    @GetMapping("/warehouse/filter")
    public String warehouseFilter(Model model){ return "warehouse/warehouse-filter"; }

    @PostMapping("/warehouse/filter/result")
    public String warehouseResult(@RequestParam String address, Model model)
    {
        List<Warehouse> result = warehouseRepository.findByAddressContains(address);
        model.addAttribute("result", result);
        return "warehouse/warehouse-filter";
    }

    @GetMapping("/warehouse/{id}")
    public String warehouseDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        ArrayList<Warehouse> res = new ArrayList<>();
        warehouse.ifPresent(res::add);
        model.addAttribute("warehouse", res);
        if(!warehouseRepository.existsById(id))
        {
            return "redirect:/warehouse";
        }
        return "/warehouse/warehouse-details";
    }

    @GetMapping("/warehouse/{id}/edit")
    public String warehouseEdit(@PathVariable("id")long id, Model model)
    {
        Iterable<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("supplier", suppliers);
        Warehouse res = warehouseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("warehouse",res);
        return "warehouse/warehouse-edit";
    }

    @PostMapping("/warehouse/{id}/edit")
    public String warehouseUpdate(@PathVariable("id")long id, @ModelAttribute("warehouse")@Valid Warehouse warehouse, @RequestParam String title, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "warehouse/warehouse-edit";
        }
        Supplier supplier = supplierRepository.findByTitle(title);
        warehouse = new Warehouse(warehouse.getAddress(), supplier);
        warehouse.setId(id);
        warehouseRepository.save(warehouse);
        return "redirect:/warehouse";
    }

    @PostMapping("/warehouse/{id}/remove")
    public String warehouseDelete(@PathVariable("id") long id, Model model){
        try{
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow();
        warehouseRepository.delete(warehouse);
        }catch (Exception ignored){}
        return "redirect:/warehouse";
    }
}
