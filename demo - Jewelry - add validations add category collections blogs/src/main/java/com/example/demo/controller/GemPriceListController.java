package com.example.demo.controller;

import com.example.demo.entity.GemPriceList;
import com.example.demo.service.DiamondService;
import com.example.demo.service.GemPriceListService;

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
public class GemPriceListController {

    @Autowired
    private GemPriceListService gemPriceListService;

    @Autowired
    private DiamondService diamondService;

    @GetMapping("/gemPriceLists")
    public String viewGemPriceListPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<GemPriceList> gemPriceListPage = gemPriceListService.getAllGemPriceLists(pageable);
        model.addAttribute("gemPriceListPage", gemPriceListPage);
        return "GemPriceList/gem_price_lists";
    }

    @GetMapping("/showNewGemPriceListForm")
    public String showNewGemPriceListForm(Model model) {
        GemPriceList gemPriceList = new GemPriceList();
        model.addAttribute("gemPriceList", gemPriceList);
        model.addAttribute("gemStones", diamondService.getAllActiveDiamonds());
        return "GemPriceList/new_gem_price_list";
    }

    @PostMapping("/saveGemPriceList")
    public String saveGemPriceList(@ModelAttribute("gemPriceList") @Valid GemPriceList gemPriceList,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("gemStones", diamondService.getAllActiveDiamonds());
            return "GemPriceList/new_gem_price_list";
        }
        gemPriceListService.saveGemPriceList(gemPriceList);
        return "redirect:/gemPriceLists";
    }

    @GetMapping("/showFormForUpdateGemPriceList/{id}")
    public String showFormForUpdateGemPriceList(@PathVariable(value = "id") int id, Model model) {
        GemPriceList gemPriceList = gemPriceListService.getGemPriceListById(id);
        model.addAttribute("gemPriceList", gemPriceList);
        model.addAttribute("gemStones", diamondService.getAllActiveDiamonds());
        return "GemPriceList/update_gem_price_list";
    }

    @PostMapping("/updateGemPriceList")
    public String updateGemPriceList(@ModelAttribute("gemPriceList") @Valid GemPriceList gemPriceList,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("gemStones", diamondService.getAllActiveDiamonds());
            return "GemPriceList/update_gem_price_list";
        }
        gemPriceListService.saveGemPriceList(gemPriceList);
        return "redirect:/gemPriceLists";
    }

    @GetMapping("/deleteGemPriceList/{id}")
    public String deleteGemPriceList(@PathVariable(value = "id") int id) {
        gemPriceListService.deleteGemPriceListById(id);
        return "redirect:/gemPriceLists";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
