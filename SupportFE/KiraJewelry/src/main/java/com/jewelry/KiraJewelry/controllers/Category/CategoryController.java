package com.jewelry.KiraJewelry.controllers.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.models.Category;
import com.jewelry.KiraJewelry.service.CategoryService;

import jakarta.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String viewCategoriesPage(Model model) {
        List<Category> listCategories = categoryService.getAllCategories();
        model.addAttribute("listCategories", listCategories);
        return "employee/manager/Category/categories";
    }

    @GetMapping("/showNewCategoryForm")
    public String showNewCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "employee/manager/Category/new_category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") @Valid Category category,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/manager/Category/new_category";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                category.setImageData(imageBytes);
                category.setImg_Url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Category/new_category";
            }
        } else if (category.getImg_Url() != null && !category.getImg_Url().isEmpty()) {
            try {
                @SuppressWarnings("deprecation")
                URL url = new URL(category.getImg_Url());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (InputStream is = url.openStream()) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, n);
                    }
                }
                byte[] imageBytes = baos.toByteArray();
                category.setImageData(imageBytes);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Could not retrieve image from URL: " + e.getMessage());
                return "employee/manager/Category/new_category";
            }
        }

        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/showFormForUpdateCategory/{id}")
    public String showFormForUpdateCategory(@PathVariable(value = "id") int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "employee/manager/Category/update_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute("category") @Valid Category category,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/manager/Category/update_category";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                category.setImageData(imageBytes);
                category.setImg_Url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Category/update_category";
            }
        } else if (category.getImg_Url() != null && !category.getImg_Url().isEmpty()) {
            // Check if there is already an existing image
            if (category.getImageData() == null || category.getImageData().length == 0) {
                try {
                    @SuppressWarnings("deprecation")
                    URL url = new URL(category.getImg_Url());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try (InputStream is = url.openStream()) {
                        byte[] buffer = new byte[1024];
                        int n;
                        while ((n = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, n);
                        }
                    }
                    byte[] imageBytes = baos.toByteArray();
                    category.setImageData(imageBytes);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Could not retrieve image from URL: " + e.getMessage());
                    return "employee/manager/Category/update_category";
                }
            }
        }

        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/deactivateCategory/{id}")
    public String deactivateCategory(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            category.setStatus(false);
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("message", "Category deactivated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Category not found.");
        }
        return "redirect:/categories";
    }

    @PostMapping("/activateCategory/{id}")
    public String activateCategory(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            category.setStatus(true);
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("message", "Category activated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Category not found.");
        }
        return "redirect:/categories";
    }

    @PostMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategoryById(id);
        redirectAttributes.addFlashAttribute("message", "Category deleted successfully.");
        return "redirect:/categories";
    }
}
