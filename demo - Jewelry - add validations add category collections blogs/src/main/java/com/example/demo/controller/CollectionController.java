package com.example.demo.controller;

import com.example.demo.entity.Collection;
import com.example.demo.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.URL;
import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/collections")
    public String viewCollectionsPage(Model model) {
        List<Collection> listCollections = collectionService.getAllCollections();
        model.addAttribute("listCollections", listCollections);
        return "Collection/collections";
    }

    @GetMapping("/showNewCollectionForm")
    public String showNewCollectionForm(Model model) {
        Collection collection = new Collection();
        model.addAttribute("collection", collection);
        return "Collection/new_collection";
    }

    @PostMapping("/saveCollection")
    public String saveCollection(@ModelAttribute("collection") @Valid Collection collection,
                                 BindingResult result,
                                 @RequestParam("imgFile") MultipartFile imgFile,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "Collection/new_collection";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                collection.setImageData(imageBytes);
                collection.setImg_Url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "Collection/new_collection";
            }
        } else if (collection.getImg_Url() != null && !collection.getImg_Url().isEmpty()) {
            try {
                @SuppressWarnings("deprecation")
                URL url = new URL(collection.getImg_Url());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (InputStream is = url.openStream()) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, n);
                    }
                }
                byte[] imageBytes = baos.toByteArray();
                collection.setImageData(imageBytes);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not retrieve image from URL: " + e.getMessage());
                return "Collection/new_collection";
            }
        }

        collectionService.saveCollection(collection);
        return "redirect:/collections";
    }

    @GetMapping("/showFormForUpdateCollection/{id}")
    public String showFormForUpdateCollection(@PathVariable(value = "id") int id, Model model) {
        Collection collection = collectionService.getCollectionById(id);
        model.addAttribute("collection", collection);
        return "Collection/update_collection";
    }

    @PostMapping("/updateCollection")
    public String updateCollection(@ModelAttribute("collection") @Valid Collection collection,
                                   BindingResult result,
                                   @RequestParam("imgFile") MultipartFile imgFile,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "Collection/update_collection";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                collection.setImageData(imageBytes);
                collection.setImg_Url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "Collection/update_collection";
            }
        } else if (collection.getImg_Url() != null && !collection.getImg_Url().isEmpty()) {
            if (collection.getImageData() == null || collection.getImageData().length == 0) {
                try {
                    @SuppressWarnings("deprecation")
                    URL url = new URL(collection.getImg_Url());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try (InputStream is = url.openStream()) {
                        byte[] buffer = new byte[1024];
                        int n;
                        while ((n = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, n);
                        }
                    }
                    byte[] imageBytes = baos.toByteArray();
                    collection.setImageData(imageBytes);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Could not retrieve image from URL: " + e.getMessage());
                    return "Collection/update_collection";
                }
            }
        }

        collectionService.saveCollection(collection);
        return "redirect:/collections";
    }

    @PostMapping("/deactivateCollection/{id}")
    public String deactivateCollection(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        Collection collection = collectionService.getCollectionById(id);
        if (collection != null) {
            collection.setStatus(false);
            collectionService.saveCollection(collection);
            redirectAttributes.addFlashAttribute("message", "Collection deactivated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Collection not found.");
        }
        return "redirect:/collections";
    }

    @PostMapping("/activateCollection/{id}")
    public String activateCollection(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        Collection collection = collectionService.getCollectionById(id);
        if (collection != null) {
            collection.setStatus(true);
            collectionService.saveCollection(collection);
            redirectAttributes.addFlashAttribute("message", "Collection activated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Collection not found.");
        }
        return "redirect:/collections";
    }

    @PostMapping("/deleteCollection/{id}")
    public String deleteCollection(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        collectionService.deleteCollectionById(id);
        redirectAttributes.addFlashAttribute("message", "Collection deleted successfully.");
        return "redirect:/collections";
    }
}
