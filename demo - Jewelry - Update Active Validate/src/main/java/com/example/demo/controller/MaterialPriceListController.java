package com.example.demo.controller;

import com.example.demo.entity.MaterialPriceList;
import com.example.demo.service.MaterialPriceListService;
import com.example.demo.service.MaterialService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;

@Controller
public class MaterialPriceListController {

    @Autowired
    private MaterialPriceListService materialPriceListService;

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materialPriceLists")
    public String viewMaterialPriceListPage(Model model) {
        List<MaterialPriceList> activeMaterialPriceLists = materialPriceListService.getAllMaterialPriceLists().stream()
                .filter(mpl -> mpl.getMaterial().getStatus() == 1)
                .collect(Collectors.toList());
        model.addAttribute("listMaterialPriceLists", activeMaterialPriceLists);
        model.addAttribute("materialPriceList", new MaterialPriceList());
        return "material_price_lists";
    }

    @GetMapping("/showNewMaterialPriceListForm")
    public String showNewMaterialPriceListForm(Model model) {
        MaterialPriceList materialPriceList = new MaterialPriceList();
        model.addAttribute("materialPriceList", materialPriceList);
        model.addAttribute("materials", materialService.getAllActiveMaterials());
        return "new_material_price_list";
    }

    @PostMapping("/saveMaterialPriceList")
    public String saveMaterialPriceList(@ModelAttribute("materialPriceList") @Valid MaterialPriceList materialPriceList,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("materials", materialService.getAllMaterials());
            return "new_material_price_list";
        }
        materialPriceListService.saveMaterialPriceList(materialPriceList);
        return "redirect:/materialPriceLists";
    }

    @GetMapping("/showFormForUpdateMaterialPriceList/{id}")
    public String showFormForUpdateMaterialPriceList(@PathVariable(value = "id") int id, Model model) {
        MaterialPriceList materialPriceList = materialPriceListService.getMaterialPriceListById(id);
        model.addAttribute("materialPriceList", materialPriceList);
        model.addAttribute("materials", materialService.getAllMaterials());
        return "update_material_price_list";
    }

    @GetMapping("/deleteMaterialPriceList/{id}")
    public String deleteMaterialPriceList(@PathVariable(value = "id") int id) {
        this.materialPriceListService.deleteMaterialPriceListById(id);
        return "redirect:/materialPriceLists";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
