package com.jewelry.KiraJewelry.controllers.Material;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import com.jewelry.KiraJewelry.service.MaterialService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        List<MaterialPriceList> activeMaterialPriceLists = materialPriceListService.getAllPriceLists().stream()
                .filter(mpl -> mpl.getMaterial().getStatus() == 1)
                .collect(Collectors.toList());
        LocalDateTime effDateTime = activeMaterialPriceLists.get(0).getEff_Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedDate = effDateTime.format(formatter);
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("listMaterialPriceLists", activeMaterialPriceLists);
        model.addAttribute("materialPriceList", new MaterialPriceList());
        return "employee/manager/MaterialPriceList/material_price_lists";
    }

    @GetMapping("/showNewMaterialPriceListForm")
    public String showNewMaterialPriceListForm(Model model) {
        try {
            MaterialPriceList materialPriceList = new MaterialPriceList();
            model.addAttribute("materialPriceList", materialPriceList);
            model.addAttribute("materials", materialService.getAllActiveMaterials());
            System.out.println("Received material: " + materialService.getAllActiveMaterials());
            System.out.println("Received materialpricelist: " + materialPriceList);
            return "employee/manager/MaterialPriceList/new_material_price_list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while loading the material price list form.");
            return "error";
        }
    }

    @PostMapping("/saveMaterialPriceList")
    public String saveMaterialPriceList(@ModelAttribute("materialPriceList") @Valid MaterialPriceList materialPriceList,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("materials", materialService.getAllActiveMaterials());
            return "employee/manager/MaterialPriceList/new_material_price_list";
        }
        materialPriceListService.saveMaterialPriceList(materialPriceList);
        return "redirect:/materialPriceLists";
    }

    @GetMapping("/showFormForUpdateMaterialPriceList/{id}")
    public String showFormForUpdateMaterialPriceList(@PathVariable(value = "id") int id, Model model) {
        MaterialPriceList materialPriceList = materialPriceListService.getMaterialPriceListById(id);
        model.addAttribute("materialPriceList", materialPriceList);
        model.addAttribute("materials", materialService.getAllActiveMaterials());
        return "employee/manager/MaterialPriceList/update_material_price_list";
    }

    @PostMapping("/updateMaterialPriceList")
    public String updateMaterialPriceList(
            @RequestParam("price") double price,
            @RequestParam("id") int id,
            Model model) {

        MaterialPriceList materialPriceList = materialPriceListService.getMaterialPriceListById(id);
        materialPriceList.setPrice(price);
        System.out.println(materialPriceList.getMaterial().getMaterial_Name() + materialPriceList.getPrice());
        materialPriceListService.saveMaterialPriceList(materialPriceList);
        return "redirect:/materialPriceLists";
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

    @GetMapping("/viewCustomerMaterialPriceListPage")
    public String viewCustomerMaterialPriceListPage(Model model, HttpServletRequest request) {
        List<MaterialPriceList> activeMaterialPriceLists = materialPriceListService.getAllPriceLists().stream()
                .filter(mpl -> mpl.getMaterial().getStatus() == 1)
                .collect(Collectors.toList());
        LocalDateTime effDateTime = activeMaterialPriceLists.get(0).getEff_Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedDate = effDateTime.format(formatter);
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("listMaterialPriceLists", activeMaterialPriceLists);
        // model.addAttribute("materialPriceList", new MaterialPriceList());
        model.addAttribute("requestURI", request.getRequestURI());
        return "price/materialPriceList";
    }
}
