package com.jewelry.KiraJewelry.controllers.Material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.MaterialService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MaterialController {

    @Autowired
    ImageService imageService;

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materials")
    public String viewMaterialsPage(Model model) {
        // Get all materials
        List<Material> allMaterials = materialService.getAllMaterials();

        // Initialize list to hold image URLs for each material
        List<String> imagesByMaterials = new ArrayList<>();

        // Iterate through each material to get its image URL
        for (Material material : allMaterials) {
            try {
                String imageUrl = imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id()));
                imagesByMaterials.add(imageUrl);
                System.out.println(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        // Add lists to the model
        model.addAttribute("imagesByMaterials", imagesByMaterials);
        model.addAttribute("listMaterials", allMaterials);
        return "employee/manager/Material/materials";
    }

    @GetMapping("/showNewMaterialForm")
    public String showNewMaterialForm(Model model) {
        if (!model.containsAttribute("material")) {
            model.addAttribute("material", new Material());
        }
        return "employee/manager/Material/new_material";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@ModelAttribute("material") @Valid Material material, BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", result);
            redirectAttributes.addFlashAttribute("material", material);
            return "redirect:/showNewMaterialForm";
        }

        materialService.saveMaterial(material);
        boolean resultSaveImg = handleImageUpload(material, imgFile, redirectAttributes);
        if (resultSaveImg == true) {
            System.out.println("Image is saved successfully on Firebase!");
        }
        return "redirect:/materials";
    }

    @GetMapping("/showFormForUpdateMaterial/{id}")
    public String showFormForUpdateMaterial(@PathVariable(value = "id") int id, Model model) throws IOException {
        if (!model.containsAttribute("material")) {
            Material material = materialService.getMaterialById(id);
            String url = imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id()));

            model.addAttribute("img_Url", url);
            model.addAttribute("material", material);
        }
        return "employee/manager/Material/update_material";
    }

    @PostMapping("/updateMaterial")
    public String updateMaterial(@ModelAttribute("material") @Valid Material material, BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", result);
            redirectAttributes.addFlashAttribute("material", material);
            return "redirect:/showFormForUpdateMaterial/" + material.getMaterial_Id();
        }

        materialService.saveMaterial(material);
        boolean resultSaveImg = handleImageUpdate(material, imgFile, redirectAttributes);
        if (resultSaveImg == true) {
            System.out.println("Image is saved successfully on Firebase!");
        }
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
                imageService.deleteImage(imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id())));
                redirectAttributes.addFlashAttribute("message", "Material deleted successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deletion: " + e.getMessage());
        }
        return "redirect:/materials";
    }

    private boolean handleImageUpload(Material material, MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (imgFile != null && !imgFile.isEmpty()) {
            try {

                String url = imageService.upload(imgFile, "Material", String.valueOf(material.getMaterial_Id()));
                return true;
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
            }
        }
        return false;
    }

    private boolean handleImageUpdate(Material material, MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                // Get the old image URL
                String oldUrl = imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id()));

                // Delete the old image if it exists
                if (oldUrl != null && !oldUrl.isEmpty()) {
                    boolean deleted = imageService.deleteImage(oldUrl);
                    if (!deleted) {
                        redirectAttributes.addFlashAttribute("errorMessage", "Could not delete old image file.");
                        return false;
                    }
                }
                boolean resultSaveImg = handleImageUpload(material, imgFile, redirectAttributes);
                if (resultSaveImg == true) {
                    System.out.println("Image is updated successfully on Firebase!");
                }
                return true;
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
            }
        }
        return false;
    }

    @GetMapping("/viewCustomerMaterialsPage")
    public String viewCustomerMaterialsPage(Model model) {
        // Get all materials
        List<Material> allMaterials = materialService.getAllMaterials();

        // Initialize list to hold image URLs for each material
        List<String> imagesByMaterials = new ArrayList<>();

        // Iterate through each material to get its image URL
        for (Material material : allMaterials) {
            try {
                String imageUrl = imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id()));
                imagesByMaterials.add(imageUrl);
                System.out.println(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        // Add lists to the model
        model.addAttribute("imagesByMaterials", imagesByMaterials);
        model.addAttribute("listMaterials", allMaterials);
        return "price/customerMaterialsPage";
    }
}
