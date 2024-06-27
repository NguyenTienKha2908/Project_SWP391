package com.jewelry.KiraJewelry.controllers.Diamond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.service.Diamond.DiamondService;

import jakarta.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @GetMapping("/diamonds")
    public String viewDiamondsPage(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

        if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
            return "redirect:/diamonds?page=" + (diamondPage.getTotalPages() - 1) + "&size=" + size;
        }

        model.addAttribute("diamondPage", diamondPage);
        return "employee/manager/Diamond/diamonds";
    }

    @GetMapping("/showNewDiamondForm")
    public String showNewDiamondForm(Model model) {
        Diamond diamond = new Diamond();
        model.addAttribute("diamond", diamond);
        return "employee/manager/Diamond/new_diamond";
    }

    @PostMapping("/addDiamond")
    public String saveDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            Model model) {
        if (result.hasErrors()) {
            return "employee/manager/Diamond/new_diamond";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                diamond.setImageData(imgFile.getBytes());
                diamond.setImgUrl(imgFile.getOriginalFilename());
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Diamond/new_diamond";
            }
        }

        diamond.setDiaCode("DIA" + (diamondService.getAllDiamonds(PageRequest.of(0, 1)).getTotalElements() + 1));
        diamond.setStatus(1); // Set status to active by default
        diamondService.saveDiamond(diamond);
        return "redirect:/diamonds";
    }

    @GetMapping("/showFormForUpdateDiamond/{id}")
    public String showFormForUpdateDiamond(@PathVariable(value = "id") int id, Model model) {
        Diamond diamond = diamondService.getDiamondById(id);
        model.addAttribute("diamond", diamond);
        return "employee/manager/Diamond/update_diamond";
    }

    @PostMapping("/updateDiamond")
    public String updateDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            Model model) {
        if (result.hasErrors()) {
            return "employee/manager/Diamond/update_diamond";
        }

        // Check if an image file is uploaded
        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                diamond.setImageData(imageBytes);
                diamond.setImgUrl(imgFile.getOriginalFilename());
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Diamond/update_diamond";
            }
        } else if (diamond.getImgUrl() != null && !diamond.getImgUrl().isEmpty()) {
            // Otherwise, if an image URL is provided, download the image and use it
            try {
                @SuppressWarnings("deprecation")
                URL url = new URL(diamond.getImgUrl());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (InputStream is = url.openStream()) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, n);
                    }
                }
                byte[] imageBytes = baos.toByteArray();
                diamond.setImageData(imageBytes);
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Could not retrieve image from URL: " + e.getMessage());
                return "employee/manager/Diamond/update_diamond";
            }
        }

        diamondService.saveDiamond(diamond);
        return "redirect:/diamonds";
    }

    @GetMapping("/deactivateDiamond/{id}")
    public String deactivateDiamond(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Diamond diamond = diamondService.getDiamondById(id);
            if (diamond == null) {
                redirectAttributes.addFlashAttribute("message", "Diamond not found.");
            } else {
                diamond.setStatus(0); // Set status to inactive
                diamondService.saveDiamond(diamond);
                redirectAttributes.addFlashAttribute("message", "Diamond deactivated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
        }
        return "redirect:/diamonds";
    }

    @GetMapping("/activateDiamond/{id}")
    public String activateDiamond(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Diamond diamond = diamondService.getDiamondById(id);
            if (diamond == null) {
                redirectAttributes.addFlashAttribute("message", "Diamond not found.");
            } else {
                diamond.setStatus(1); // Set status to active
                diamondService.saveDiamond(diamond);
                redirectAttributes.addFlashAttribute("message", "Diamond activated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during activation: " + e.getMessage());
        }
        return "redirect:/diamonds";
    }
}
