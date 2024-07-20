package com.example.demo.controller;

import com.example.demo.entity.DiamondPriceList;
import com.example.demo.service.DiamondPriceListService;
import com.example.demo.service.DiamondService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DiamondPriceListController {

    @Autowired
    private DiamondPriceListService diaPriceListService;

    @Autowired
    private DiamondService diamondService;

    @GetMapping("/diaPriceLists")
    public String viewDiaPriceListPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<DiamondPriceList> diaPriceListPage = diaPriceListService.getAllDiaPriceLists(pageable);
        model.addAttribute("diaPriceListPage", diaPriceListPage);
        return "DiamondPriceList/dia_price_lists";
    }

    @GetMapping("/showNewDiaPriceListForm")
    public String showNewDiaPriceListForm(Model model) {
        DiamondPriceList diaPriceList = new DiamondPriceList();
        model.addAttribute("diaPriceList", diaPriceList);
        model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());
        return "DiamondPriceList/new_dia_price_list";
    }

    @PostMapping("/saveDiaPriceList")
    public String saveDiaPriceList(@ModelAttribute("diaPriceList") @Valid DiamondPriceList diaPriceList,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());
            return "DiamondPriceList/new_dia_price_list";
        }
        diaPriceListService.saveDiaPriceList(diaPriceList);
        return "redirect:/diaPriceLists";
    }

    @GetMapping("/showFormForUpdateDiaPriceList/{id}")
    public String showFormForUpdateDiaPriceList(@PathVariable(value = "id") int id, Model model) {
        DiamondPriceList diaPriceList = diaPriceListService.getDiaPriceListById(id);
        model.addAttribute("diaPriceList", diaPriceList);
        model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());
        return "DiamondPriceList/update_dia_price_list";
    }

    @PostMapping("/updateDiaPriceList")
    public String updateDiaPriceList(@ModelAttribute("diaPriceList") @Valid DiamondPriceList diaPriceList,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());
            return "DiamondPriceList/update_dia_price_list";
        }
        diaPriceListService.saveDiaPriceList(diaPriceList);
        return "redirect:/diaPriceLists";
    }

    @GetMapping("/deleteDiaPriceList/{id}")
    public String deleteDiaPriceList(@PathVariable(value = "id") int id) {
        diaPriceListService.deleteDiaPriceListById(id);
        return "redirect:/diaPriceLists";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
