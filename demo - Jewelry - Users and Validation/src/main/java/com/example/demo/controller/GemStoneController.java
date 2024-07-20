package com.example.demo.controller;

import com.example.demo.entity.GemStone;
import com.example.demo.service.GemStoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class GemStoneController {

    @Autowired
    private GemStoneService gemstoneService;

    @GetMapping("/gemstones")
    public String viewGemstonesPage(Model model, 
                                    @RequestParam(defaultValue = "0") int page, 
                                    @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GemStone> gemstonePage = gemstoneService.getAllGemStones(pageable);
        model.addAttribute("gemstonePage", gemstonePage);
        return "GemStone/gemstones";
    }

    @GetMapping("/showNewGemstoneForm")
    public String showNewGemstoneForm(Model model) {
        GemStone gemstone = new GemStone();
        model.addAttribute("gemstone", gemstone);
        return "GemStone/new_gemstone";
    }

    @PostMapping("/saveGemstone")
    public String saveGemstone(@ModelAttribute("gemstone") @Valid GemStone gemstone, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "GemStone/new_gemstone";
        }
        gemstone.setGemCode("DIA" + (gemstoneService.getAllGemStones(PageRequest.of(0, 1)).getTotalElements() + 1));
        gemstone.setStatus(1); // Set status to active by default
        gemstoneService.saveGemStone(gemstone);
        return "redirect:/gemstones";
    }

    @GetMapping("/showFormForUpdateGemstone/{id}")
    public String showFormForUpdateGemstone(@PathVariable(value = "id") int id, Model model) {
        GemStone gemstone = gemstoneService.getGemStoneById(id);
        model.addAttribute("gemstone", gemstone);
        return "GemStone/update_gemstone";
    }

    @PostMapping("/updateGemstone")
    public String updateGemstone(@ModelAttribute("gemstone") @Valid GemStone gemstone, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "GemStone/update_gemstone";
        }
        GemStone existingGemstone = gemstoneService.getGemStoneById(gemstone.getGemId());
        if (existingGemstone != null) {
            gemstone.setStatus(existingGemstone.getStatus()); // Keep the status unchanged
            gemstoneService.saveGemStone(gemstone);
        }
        return "redirect:/gemstones";
    }
    

    @GetMapping("/deactivateGemstone/{id}")
    public String deactivateGemstone(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            GemStone gemstone = gemstoneService.getGemStoneById(id);
            if (gemstone == null) {
                redirectAttributes.addFlashAttribute("message", "Gemstone not found.");
            } else {
                gemstone.setStatus(0); // Set status to inactive
                gemstoneService.saveGemStone(gemstone);
                redirectAttributes.addFlashAttribute("message", "Gemstone deactivated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
        }
        return "redirect:/gemstones";
    }

    @GetMapping("/activateGemstone/{id}")
    public String activateGemstone(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            GemStone gemstone = gemstoneService.getGemStoneById(id);
            if (gemstone == null) {
                redirectAttributes.addFlashAttribute("message", "Gemstone not found.");
            } else {
                gemstone.setStatus(1); // Set status to active
                gemstoneService.saveGemStone(gemstone);
                redirectAttributes.addFlashAttribute("message", "Gemstone activated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during activation: " + e.getMessage());
        }
        return "redirect:/gemstones";
    }
}
