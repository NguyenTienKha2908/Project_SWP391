package com.example.demo.controller;

import com.example.demo.entity.Material;
import com.example.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materials")
    public String viewMaterialsPage(Model model) {
        model.addAttribute("listMaterials", materialService.getAllMaterials());
        return "materials";
    }

    @GetMapping("/showNewMaterialForm")
    public String showNewMaterialForm(Model model) {
        Material material = new Material();
        model.addAttribute("material", material);
        return "new_material";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@ModelAttribute("material") Material material) {
        materialService.saveMaterial(material);
        return "redirect:/materials";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Material material = materialService.getMaterialById(id);
        model.addAttribute("material", material);
        return "update_material";
    }

    @GetMapping("/deleteMaterial/{id}")
    public String toggleMaterialStatus(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Material material = materialService.getMaterialById(id);
            if (material == null) {
                redirectAttributes.addFlashAttribute("message", "Material not found.");
            } else {
                // Toggle material status
                int currentStatus = material.getStatus();
                material.setStatus(currentStatus == 0 ? 1 : 0);
                materialService.saveMaterial(material);
                redirectAttributes.addFlashAttribute("message", "Material status updated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during status update: " + e.getMessage());
        }
        return "redirect:/materials";
    }

}
