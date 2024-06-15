// package com.example.demo.controller;

// import com.example.demo.entity.Diamond;
// import com.example.demo.service.DiamondService;
// import com.example.demo.validation.FileUploadUtil;
// import com.microsoft.sqlserver.jdbc.StringUtils;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import jakarta.validation.Valid;
// import lombok.Value;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// @Controller
// public class DiamondController {

//     @Autowired
//     private DiamondService diamondService;

//     @Value("${upload.path}")
//     private String uploadPath;

//     @GetMapping("/diamonds")
//     public String viewDiamondsPage(Model model,
//                                    @RequestParam(defaultValue = "0") int page,
//                                    @RequestParam(defaultValue = "20") int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

//         if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
//             return "redirect:/diamonds?page=" + (diamondPage.getTotalPages() - 1) + "&size=" + size;
//         }

//         model.addAttribute("diamondPage", diamondPage);
//         return "Diamond/diamonds";
//     }

//     @GetMapping("/showNewDiamondForm")
//     public String showNewDiamondForm(Model model) {
//         Diamond diamond = new Diamond();
//         model.addAttribute("diamond", diamond);
//         return "Diamond/new_diamond";
//     }

//     @PostMapping("/saveDiamond")
//     public String saveDiamond(@ModelAttribute("diamond") @Valid Diamond diamond, BindingResult result, Model model) {
//         if (result.hasErrors()) {
//             return "Diamond/new_diamond";
//         }

//         // Handle file upload
//         MultipartFile file = diamond.getImgFile();
//         if (file != null && !file.isEmpty()) {
//             try {
//                 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//                 String uploadDir = uploadPath + "/diamonds";
//                 FileUploadUtil.saveFile(uploadDir, fileName, file);
//                 diamond.setImgUrl("/diamonds/" + fileName);
//             } catch (IOException e) {
//                 model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
//                 return "Diamond/new_diamond";
//             }
//         }

//         diamond.setGemCode("DIA" + (diamondService.getAllDiamonds(PageRequest.of(0, 1)).getTotalElements() + 1));
//         diamond.setStatus(1); // Set status to active by default
//         diamondService.saveDiamond(diamond);
//         return "redirect:/diamonds";
//     }

//     @GetMapping("/showFormForUpdateDiamond/{id}")
//     public String showFormForUpdateDiamond(@PathVariable(value = "id") int id, Model model) {
//         Diamond diamond = diamondService.getDiamondById(id);
//         model.addAttribute("diamond", diamond);
//         return "Diamond/update_diamond";
//     }

//     @PostMapping("/updateDiamond")
//     public String updateDiamond(@ModelAttribute("diamond") @Valid Diamond diamond, BindingResult result, Model model) {
//         if (result.hasErrors()) {
//             return "Diamond/update_diamond";
//         }

//         // Handle file upload
//         MultipartFile file = diamond.getImgFile();
//         if (file != null && !file.isEmpty()) {
//             try {
//                 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//                 String uploadDir = uploadPath + "/diamonds";
//                 FileUploadUtil.saveFile(uploadDir, fileName, file);
//                 diamond.setImgUrl("/diamonds/" + fileName);
//             } catch (IOException e) {
//                 model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
//                 return "Diamond/update_diamond";
//             }
//         }

//         Diamond existingDiamond = diamondService.getDiamondById(diamond.getGemId());
//         if (existingDiamond != null) {
//             diamond.setStatus(existingDiamond.getStatus()); // Keep the status unchanged
//             diamondService.saveDiamond(diamond);
//         }
//         return "redirect:/diamonds";
//     }

//     @GetMapping("/deactivateDiamond/{id}")
//     public String deactivateDiamond(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
//         try {
//             Diamond diamond = diamondService.getDiamondById(id);
//             if (diamond == null) {
//                 redirectAttributes.addFlashAttribute("message", "Diamond not found.");
//             } else {
//                 diamond.setStatus(0); // Set status to inactive
//                 diamondService.saveDiamond(diamond);
//                 redirectAttributes.addFlashAttribute("message", "Diamond deactivated successfully.");
//             }
//         } catch (Exception e) {
//             redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
//         }
//         return "redirect:/diamonds";
//     }

//     @GetMapping("/activateDiamond/{id}")
//     public String activateDiamond(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
//         try {
//             Diamond diamond = diamondService.getDiamondById(id);
//             if (diamond == null) {
//                 redirectAttributes.addFlashAttribute("message", "Diamond not found.");
//             } else {
//                 diamond.setStatus(1); // Set status to active
//                 diamondService.saveDiamond(diamond);
//                 redirectAttributes.addFlashAttribute("message", "Diamond activated successfully.");
//             }
//         } catch (Exception e) {
//             redirectAttributes.addFlashAttribute("message", "Error during activation: " + e.getMessage());
//         }
//         return "redirect:/diamonds";
//     }
// }



// @Controller
// public class DiamondController {

//     @Autowired
//     private DiamondService diamondService;

//     @GetMapping("/diamonds")
//     public String viewDiamondsPage(Model model,
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "20") int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

//         if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
//             return "redirect:/diamonds?page=" + (diamondPage.getTotalPages() - 1) + "&size=" + size;
//         }

//         model.addAttribute("diamondPage", diamondPage);
//         return "Diamond/diamonds";
//     }

//     @GetMapping("/showNewDiamondForm")
//     public String showNewDiamondForm(Model model) {
//         Diamond diamond = new Diamond();
//         model.addAttribute("diamond", diamond);
//         return "Diamond/new_diamond";
//     }

//     @PostMapping("/saveDiamond")
//     public String saveDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
//             BindingResult result,
//             @RequestParam("imgFile") MultipartFile imgFile,
//             Model model) {
//         if (result.hasErrors()) {
//             return "Diamond/new_diamond";
//         }

//         if (imgFile != null && !imgFile.isEmpty()) {
//             try {
//                 diamond.setImageData(imgFile.getBytes());
//                 diamond.setImgUrl(imgFile.getOriginalFilename());
//             } catch (IOException e) {
//                 model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
//                 return "Diamond/new_diamond";
//             }
//         }

//         diamond.setGemCode("DIA" + (diamondService.getAllDiamonds(PageRequest.of(0, 1)).getTotalElements() + 1));
//         diamond.setStatus(1); // Set status to active by default
//         diamondService.saveDiamond(diamond);
//         return "redirect:/diamonds";
//     }

//     @GetMapping("/showFormForUpdateDiamond/{id}")
//     public String showFormForUpdateDiamond(@PathVariable(value = "id") int id, Model model) {
//         Diamond diamond = diamondService.getDiamondById(id);
//         model.addAttribute("diamond", diamond);
//         return "Diamond/update_diamond";
//     }

// @PostMapping("/updateDiamond")
// public String updateDiamond(@ModelAttribute("diamond") @Valid Diamond
// diamond,
// BindingResult result,
// @RequestParam("imgFile") MultipartFile imgFile,
// Model model) {
// if (result.hasErrors()) {
// return "Diamond/update_diamond";
// }

// Diamond existingDiamond = diamondService.getDiamondById(diamond.getGemId());
// if (existingDiamond != null) {
// if (imgFile != null && !imgFile.isEmpty()) {
// try {
// diamond.setImageData(imgFile.getBytes());
// diamond.setImgUrl(imgFile.getOriginalFilename());
// } catch (IOException e) {
// model.addAttribute("errorMessage", "Could not save image file: " +
// e.getMessage());
// return "Diamond/update_diamond";
// }
// } else {
// diamond.setImgUrl(existingDiamond.getImgUrl()); // Retain existing image URL
// if no new image is uploaded
// }

// diamond.setStatus(existingDiamond.getStatus()); // Keep the status unchanged
// diamondService.saveDiamond(diamond);
// }
// return "redirect:/diamonds";
// }

package com.example.demo.controller;

import com.example.demo.entity.Diamond;
import com.example.demo.service.DiamondService;
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
        return "Diamond/diamonds";
    }

    @GetMapping("/showNewDiamondForm")
    public String showNewDiamondForm(Model model) {
        Diamond diamond = new Diamond();
        model.addAttribute("diamond", diamond);
        return "Diamond/new_diamond";
    }

    @PostMapping("/addDiamond")
    public String saveDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            Model model) {
        if (result.hasErrors()) {
            return "Diamond/new_diamond";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                diamond.setImageData(imgFile.getBytes());
                diamond.setImgUrl(imgFile.getOriginalFilename());
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "Diamond/new_diamond";
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
        return "Diamond/update_diamond";
    }

    @PostMapping("/updateDiamond")
    public String updateDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
                                BindingResult result,
                                @RequestParam("imgFile") MultipartFile imgFile,
                                Model model) {
        if (result.hasErrors()) {
            return "Diamond/update_diamond";
        }

        // Check if an image file is uploaded
        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                diamond.setImageData(imageBytes);
                diamond.setImgUrl(imgFile.getOriginalFilename());
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "Diamond/update_diamond";
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
                return "Diamond/update_diamond";
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

