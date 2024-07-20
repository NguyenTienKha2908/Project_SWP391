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
    public String deleteMaterial(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            if (!materialService.canDeleteMaterial(id)) {
                redirectAttributes.addFlashAttribute("message",
                        "Cannot delete material as it is linked with a price list.");
            } else {
                materialService.deleteMaterialById(id);
                redirectAttributes.addFlashAttribute("message", "Material deleted successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deletion: " + e.getMessage());
        }
        return "redirect:/materials";
    }

}
