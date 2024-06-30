package com.jewelry.KiraJewelry.controllers.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.models.Category;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.service.CategoryService;
import com.jewelry.KiraJewelry.service.ImageService;

import jakarta.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ImageService imageService;

    @GetMapping("/categories")
    public String viewCategoriesPage(Model model) {
        List<Category> listCategories = categoryService.getAllCategories();
        model.addAttribute("listCategories", listCategories);

        List<String> imagesByCategory = new ArrayList<>();

        // Iterate through each material to get its image URL
        for (Category category : listCategories) {
            try {
                String imageUrl = imageService.getImgByCateogryID(String.valueOf(category.getCategory_Id()));
                imagesByCategory.add(imageUrl);
                System.out.println(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        model.addAttribute("imagesByCategory", imagesByCategory);
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
        categoryService.saveCategory(category);
        boolean resultSaveImg = handleImageUpload(category, imgFile, redirectAttributes);
        if (resultSaveImg == true) {
            System.out.println("Image is saved successfully on Firebase!");
        }
        return "redirect:/categories";
    }

    @GetMapping("/showFormForUpdateCategory/{id}")
    public String showFormForUpdateCategory(@PathVariable(value = "id") int id, Model model) throws IOException {
        Category category = categoryService.getCategoryById(id);
        String url = imageService.getImgByCateogryID(String.valueOf(category.getCategory_Id()));

        model.addAttribute("img_Url", url);
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

        boolean resultSaveImg = handleImageUpdate(category, imgFile, redirectAttributes);
        if (resultSaveImg == true) {
            System.out.println("Image is updated successfully on Firebase!");
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
    public String deleteCategory(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) throws IOException {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategoryById(id);
        if(String.valueOf(category.getCategory_Id())!=null) {
            imageService.deleteImage(imageService.getImgByCateogryID(String.valueOf(category.getCategory_Id())));
        } 
        redirectAttributes.addFlashAttribute("message", "Category deleted successfully.");
        return "redirect:/categories";
    }

    private boolean handleImageUpload(Category category, MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (imgFile != null && !imgFile.isEmpty()) {
            try {

                String url = imageService.upload(imgFile, "Category", String.valueOf(category.getCategory_Id()));
                return true;
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
            }
        }
        return false;
    }

    private boolean handleImageUpdate(Category category, MultipartFile imgFile, RedirectAttributes redirectAttributes) {
        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                // Get the old image URL
                String oldUrl = imageService.getImgByMaterialID(String.valueOf(category.getCategory_Id()));

                // Delete the old image if it exists
                if (oldUrl != null && !oldUrl.isEmpty()) {
                    boolean deleted = imageService.deleteImage(oldUrl);
                    if (!deleted) {
                        redirectAttributes.addFlashAttribute("errorMessage", "Could not delete old image file.");
                        return false;
                    }
                }
                boolean resultSaveImg = handleImageUpload(category, imgFile, redirectAttributes);
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
}
