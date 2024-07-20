package com.example.demo.controller;

import com.example.demo.entity.Material;
import com.example.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MaterialController {

    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materials")
    public String viewMaterialsPage(Model model) {
        model.addAttribute("listMaterials", materialService.getAllMaterials());
        return "Material/materials";
    }

    @GetMapping("/showNewMaterialForm")
    public String showNewMaterialForm(Model model) {
        if (!model.containsAttribute("material")) {
            model.addAttribute("material", new Material());
        }
        return "Material/new_material";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@ModelAttribute("material") @Valid Material material, BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", result);
            redirectAttributes.addFlashAttribute("material", material);
            return "redirect:/showNewMaterialForm";
        }

        handleImageUpload(material, imgFile, redirectAttributes);

        materialService.saveMaterial(material);
        return "redirect:/materials";
    }

    @GetMapping("/showFormForUpdateMaterial/{id}")
    public String showFormForUpdateMaterial(@PathVariable(value = "id") int id, Model model) {
        if (!model.containsAttribute("material")) {
            Material material = materialService.getMaterialById(id);
            model.addAttribute("material", material);
        }
        return "Material/update_material";
    }

    @PostMapping("/updateMaterial")
    public String updateMaterial(@ModelAttribute("material") @Valid Material material, BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", result);
            redirectAttributes.addFlashAttribute("material", material);
            return "redirect:/showFormForUpdateMaterial/" + material.getMaterialId();
        }

        handleImageUpload(material, imgFile, redirectAttributes);

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

    @GetMapping("/deleteMaterial/{id}")
    public String deleteMaterial(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Material material = materialService.getMaterialById(id);
            if (material == null) {
                redirectAttributes.addFlashAttribute("message", "Material not found.");
            } else {
                materialService.deleteMaterialById(id);
                redirectAttributes.addFlashAttribute("message", "Material deleted successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deletion: " + e.getMessage());
        }
        return "redirect:/materials";
    }

    private void handleImageUpload(Material material, MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                String filename = imgFile.getOriginalFilename();
                Files.copy(imgFile.getInputStream(), this.rootLocation.resolve(filename));
                material.setImgUrl("/materials/image/" + filename);
                material.setImageData(imgFile.getBytes());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
            }
        } else if (material.getImgUrl() != null && !material.getImgUrl().isEmpty()) {
            try {
                @SuppressWarnings("deprecation")
                URL url = new URL(material.getImgUrl());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (InputStream is = url.openStream()) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, n);
                    }
                }
                byte[] imageBytes = baos.toByteArray();
                material.setImageData(imageBytes);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Could not retrieve image from URL: " + e.getMessage());
            }
        }
    }

    @GetMapping("/materials/image/{filename:.+}")
    @ResponseBody
    public Resource getImage(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
}
