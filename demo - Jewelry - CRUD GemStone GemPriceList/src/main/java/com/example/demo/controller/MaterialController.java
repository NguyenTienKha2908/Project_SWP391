package com.example.demo.controller;

import com.example.demo.entity.Material;
import com.example.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materials")
    public String viewMaterialsPage(Model model) {
        model.addAttribute("listMaterials", materialService.getAllMaterials());
        return "Material/materials";
    }

    @GetMapping("/showNewMaterialForm")
    public String showNewMaterialForm(Model model) {
        Material material = new Material();
        model.addAttribute("material", material);
        return "Material/new_material";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@ModelAttribute("material") @Valid Material material, Model model) {
        materialService.saveMaterial(material);
        return "redirect:/materials";
    }

    @GetMapping("/showFormForUpdateMaterial/{id}")
    public String showFormForUpdateMaterial(@PathVariable(value = "id") int id, Model model) {
        Material material = materialService.getMaterialById(id);
        model.addAttribute("material", material);
        return "Material/update_material";
    }

    @PostMapping("/updateMaterial")
    public String updateMaterial(@ModelAttribute("material") @Valid Material material, Model model) {
        materialService.saveMaterial(material);
        return "redirect:/materials";
    }

    @GetMapping("/deactivateMaterial/{id}")
    public String deactivateMaterial(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Material material = materialService.getMaterialById(id);
            if (material == null) {
                redirectAttributes.addFlashAttribute("message", "Material not found.");
            } else {
                material.setStatus(0); // Set status to inactive
                materialService.saveMaterial(material);
                redirectAttributes.addFlashAttribute("message", "Material deactivated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
        }
        return "redirect:/materials";
    }

    @GetMapping("/activateMaterial/{id}")
    public String activateMaterial(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Material material = materialService.getMaterialById(id);
            if (material == null) {
                redirectAttributes.addFlashAttribute("message", "Material not found.");
            } else {
                material.setStatus(1); // Set status to active
                materialService.saveMaterial(material);
                redirectAttributes.addFlashAttribute("message", "Material activated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during activation: " + e.getMessage());
        }
        return "redirect:/materials";
    }
}
