package com.example.Autoshop.controllers;

import com.example.Autoshop.models.*;
import com.example.Autoshop.repo.*;
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
public class SparePartController {
    @Autowired
    private SparePartRepository sparePartRepository;

    @Autowired
    private SparePartTypeRepository sparePartTypeRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping("/sparePart")
    public String sparePartMain(Model model){
        Iterable<SparePart> spareParts = sparePartRepository.findAll();
        model.addAttribute("sparePart", spareParts);
        return "sparePart/sparePart-main";
    }

    @GetMapping("/sparePart/add")
    public String sparePartAdd(SparePart sparePart, Model model) {
        Iterable<SparePartType> sparePartTypes = sparePartTypeRepository.findAll();
        model.addAttribute("sparePartType", sparePartTypes);
        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("country", countries);
        Iterable<Brand> brands = brandRepository.findAll();
        model.addAttribute("brand", brands);
        Iterable<CarBrand> carBrands = carBrandRepository.findAll();
        model.addAttribute("carBrand", carBrands);
        Iterable<Warehouse> warehouses = warehouseRepository.findAll();
        model.addAttribute("warehouse", warehouses);
        return "sparePart/sparePart-add";
    }

    @PostMapping("/sparePart/add")
    public String sparePartNewAdd(@ModelAttribute("sparePart") @Valid SparePart sparePart, @RequestParam String sparePartTypeType,
                                  @RequestParam String countryTitle, @RequestParam String brandTitle,
            @RequestParam String carBrandTitle, @RequestParam String warehouseAddress, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "sparePart/sparePart-add";
        }
        SparePartType sparePartType = sparePartTypeRepository.findSparePartTypeByType(sparePartTypeType);
        Country country = countryRepository.findByTitle(countryTitle);
        Brand brand = brandRepository.findByTitle(brandTitle);
        CarBrand carBrand = carBrandRepository.findByTitle(carBrandTitle);
        Warehouse warehouse = warehouseRepository.findByAddress(warehouseAddress);
        sparePart = new SparePart(sparePart.getTitle(), sparePartType, country, brand, carBrand, warehouse);
        sparePartRepository.save(sparePart);
        return "redirect:/sparePart";
    }

    @GetMapping("/sparePart/filter")
    public String sparePartFilter(Model model){ return "sparePart/sparePart-filter"; }

    @PostMapping("/sparePart/filter/result")
    public String sparePartResult(@RequestParam String title, Model model)
    {
        List<SparePart> result = sparePartRepository.findSparePartByTitleContains(title);
        model.addAttribute("result", result);
        return "sparePart/sparePart-filter";
    }

    @GetMapping("/sparePart/{id}")
    public String sparePartDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<SparePart> sparePart = sparePartRepository.findById(id);
        ArrayList<SparePart> res = new ArrayList<>();
        sparePart.ifPresent(res::add);
        model.addAttribute("sparePart", res);
        if(!sparePartRepository.existsById(id))
        {
            return "redirect:/sparePart";
        }
        return "/sparePart/sparePart-details";
    }

    @GetMapping("/sparePart/{id}/edit")
    public String sparePartEdit(@PathVariable("id")long id, Model model)
    {
        Iterable<SparePartType> sparePartTypes = sparePartTypeRepository.findAll();
        model.addAttribute("sparePartType", sparePartTypes);
        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("country", countries);
        Iterable<Brand> brands = brandRepository.findAll();
        model.addAttribute("brand", brands);
        Iterable<CarBrand> carBrands = carBrandRepository.findAll();
        model.addAttribute("carBrand", carBrands);
        Iterable<Warehouse> warehouses = warehouseRepository.findAll();
        model.addAttribute("warehouse", warehouses);
        SparePart res = sparePartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id:" + id));
        model.addAttribute("sparePart",res);
        return "sparePart/sparePart-edit";
    }

    @PostMapping("/sparePart/{id}/edit")
    public String sparePartUpdate(@PathVariable("id")long id, @ModelAttribute("sparePart")@Valid SparePart sparePart, @RequestParam String sparePartTypeType,
                                  @RequestParam String countryTitle, @RequestParam String brandTitle,
                                  @RequestParam String carBrandTitle, @RequestParam String warehouseAddress,BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "sparePart/sparePart-edit";
        }
        SparePartType sparePartType = sparePartTypeRepository.findSparePartTypeByType(sparePartTypeType);
        Country country = countryRepository.findByTitle(countryTitle);
        Brand brand = brandRepository.findByTitle(brandTitle);
        CarBrand carBrand = carBrandRepository.findByTitle(carBrandTitle);
        Warehouse warehouse = warehouseRepository.findByAddress(warehouseAddress);
        sparePart = new SparePart(sparePart.getTitle(), sparePartType, country, brand, carBrand, warehouse);
        sparePart.setId(id);
        sparePartRepository.save(sparePart);
        return "redirect:/sparePart";
    }

    @PostMapping("/sparePart/{id}/remove")
    public String sparePartDelete(@PathVariable("id") long id, Model model){
        try{
        SparePart sparePart = sparePartRepository.findById(id).orElseThrow();
        sparePartRepository.delete(sparePart);
        }catch (Exception ignored){}
        return "redirect:/sparePart";
    }
}
