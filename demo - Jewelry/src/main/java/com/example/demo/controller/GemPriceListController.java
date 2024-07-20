package com.example.demo.controller;

import com.example.demo.entity.GemPriceList;
import com.example.demo.service.GemPriceListService;
import com.example.demo.service.GemStoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class GemPriceListController {

    @Autowired
    private GemPriceListService gemPriceListService;

    @Autowired
    private GemStoneService gemStoneService;

    @GetMapping("/gemPriceLists")
    public String viewGemPriceListPage(Model model) {
        List<GemPriceList> gemPriceLists = gemPriceListService.getAllGemPriceLists();
        model.addAttribute("listGemPriceLists", gemPriceLists);
        return "GemPriceList/gem_price_lists";
    }

    @GetMapping("/showNewGemPriceListForm")
    public String showNewGemPriceListForm(Model model) {
        GemPriceList gemPriceList = new GemPriceList();
        model.addAttribute("gemPriceList", gemPriceList);
        model.addAttribute("gemStones", gemStoneService.getAllActiveGemStones());
        return "GemPriceList/new_gem_price_list";
    }

    @PostMapping("/saveGemPriceList")
    public String saveGemPriceList(@ModelAttribute("gemPriceList") @Valid GemPriceList gemPriceList,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("gemStones", gemStoneService.getAllActiveGemStones());
            return "GemPriceList/new_gem_price_list";
        }
        gemPriceListService.saveGemPriceList(gemPriceList);
        return "redirect:/gemPriceLists";
    }

    @GetMapping("/showFormForUpdateGemPriceList/{id}")
    public String showFormForUpdateGemPriceList(@PathVariable(value = "id") int id, Model model) {
        GemPriceList gemPriceList = gemPriceListService.getGemPriceListById(id);
        model.addAttribute("gemPriceList", gemPriceList);
        model.addAttribute("gemStones", gemStoneService.getAllActiveGemStones());
        return "GemPriceList/update_gem_price_list";
    }

    @PostMapping("/updateGemPriceList")
    public String updateGemPriceList(@ModelAttribute("gemPriceList") @Valid GemPriceList gemPriceList,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("gemStones", gemStoneService.getAllActiveGemStones());
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
