// DiamondController.java
package com.example.demo.controller;

import com.example.demo.entity.Diamond;
import com.example.demo.entity.DiamondPriceList;
import com.example.demo.service.DiamondPriceListService;
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
import java.util.List;

@Controller
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private DiamondPriceListService diamondPriceListService;

    @GetMapping("/diamonds")
    public String viewDiamondsPage(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size,
                                   @RequestParam(value = "message", required = false) String message) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

        if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
            return "redirect:/diamonds?page=" + (diamondPage.getTotalPages() - 1) + "&size=" + size;
        }

        model.addAttribute("diamondPage", diamondPage);
        model.addAttribute("message", message);
        return "Diamond/diamonds";
    }

    @GetMapping("/showNewDiamondForm")
    public String showNewDiamondForm(Model model) {
        Diamond diamond = new Diamond();
        diamond.setDia_code(generateDiamondCode());
        diamond.setO_price(0.0f); // Set default O Price
        model.addAttribute("diamond", diamond);

        List<String> origins = diamondPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        return "Diamond/new_diamond";
    }

    @PostMapping("/addDiamond")
    public String saveDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
                              BindingResult result,
                              @RequestParam("imgFile") MultipartFile imgFile,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<String> origins = diamondPriceListService.getAllOrigins();
            model.addAttribute("origins", origins);
            return "Diamond/new_diamond";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                diamond.setImage_data(imgFile.getBytes());
                diamond.setImg_url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "Diamond/new_diamond";
            }
        }

        if (diamond.getDia_id() == 0) { // Only generate a new code if the diamond is new
            diamond.setDia_code(generateDiamondCode());
        }
        diamond.setStatus(1); // Set status to active by default
        diamond.setO_price(0.0f); // Ensure O Price is set to 0

        DiamondPriceList priceList = diamondPriceListService.getPriceByDetails(
                diamond.getOrigin(), diamond.getCarat_weight(), diamond.getColor(),
                diamond.getClarity(), diamond.getCut());
        if (priceList != null) {
            diamond.setQ_price(priceList.getPrice());
        } else {
            model.addAttribute("errorMessage", "No matching price found for the provided diamond details.");
            return "Diamond/new_diamond";
        }

        diamondService.saveDiamond(diamond);
        redirectAttributes.addAttribute("message", "Diamond " + diamond.getDia_code() + " has been added successfully.");
        return "redirect:/diamonds";
    }

    @GetMapping("/showFormForUpdateDiamond/{id}")
    public String showFormForUpdateDiamond(@PathVariable(value = "id") int id, Model model) {
        Diamond diamond = diamondService.getDiamondById(id);
        model.addAttribute("diamond", diamond);

        List<String> origins = diamondPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        List<String> colors = diamondPriceListService.getColorsByOriginAndCaratWeight(diamond.getOrigin(),
                diamond.getCarat_weight());
        model.addAttribute("colors", colors);

        List<String> clarities = diamondPriceListService.getClaritiesByOriginCaratWeightAndColor(diamond.getOrigin(),
                diamond.getCarat_weight(), diamond.getColor());
        model.addAttribute("clarities", clarities);

        List<String> cuts = diamondPriceListService.getCutsByOriginCaratWeightColorAndClarity(diamond.getOrigin(),
                diamond.getCarat_weight(), diamond.getColor(), diamond.getClarity());
        model.addAttribute("cuts", cuts);

        return "Diamond/update_diamond";
    }

    @PostMapping("/updateDiamond")
    public String updateDiamond(@ModelAttribute("diamond") @Valid Diamond diamond,
                                BindingResult result,
                                @RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            prepareModelForUpdateForm(model, diamond);
            return "Diamond/update_diamond";
        }

        try {
            handleImageFile(diamond, imgFile);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
            prepareModelForUpdateForm(model, diamond);
            return "Diamond/update_diamond";
        }

        DiamondPriceList priceList = diamondPriceListService.getPriceByDetails(
                diamond.getOrigin(), diamond.getCarat_weight(), diamond.getColor(),
                diamond.getClarity(), diamond.getCut());
        if (priceList != null) {
            diamond.setQ_price(priceList.getPrice());
            System.out.println("Price found: " + priceList.getPrice()); // Logging the price
        } else {
            model.addAttribute("errorMessage", "No matching price found for the provided diamond details.");
            prepareModelForUpdateForm(model, diamond);
            return "Diamond/update_diamond";
        }

        System.out.println("Updating diamond: " + diamond); // Logging the diamond details
        diamondService.saveDiamond(diamond);
        System.out.println("Diamond saved successfully");

        redirectAttributes.addAttribute("message", "Diamond " + diamond.getDia_code() + " has been updated successfully.");
        return "redirect:/diamonds";
    }

    private void handleImageFile(Diamond diamond, MultipartFile imgFile) throws IOException {
        if (imgFile != null && !imgFile.isEmpty()) {
            diamond.setImage_data(imgFile.getBytes());
            diamond.setImg_url(imgFile.getOriginalFilename());
        } else if (diamond.getImg_url() != null && !diamond.getImg_url().isEmpty()) {
            byte[] imageBytes = fetchImageFromURL(diamond.getImg_url());
            diamond.setImage_data(imageBytes);
        }
    }

    private byte[] fetchImageFromURL(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             InputStream is = url.openStream()) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = is.read(buffer)) != -1) {
                baos.write(buffer, 0, n);
            }
            return baos.toByteArray();
        }
    }

    private void prepareModelForUpdateForm(Model model, Diamond diamond) {
        List<String> origins = diamondPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        List<String> colors = diamondPriceListService.getColorsByOriginAndCaratWeight(diamond.getOrigin(),
                diamond.getCarat_weight());
        model.addAttribute("colors", colors);

        List<String> clarities = diamondPriceListService.getClaritiesByOriginCaratWeightAndColor(diamond.getOrigin(),
                diamond.getCarat_weight(), diamond.getColor());
        model.addAttribute("clarities", clarities);

        List<String> cuts = diamondPriceListService.getCutsByOriginCaratWeightColorAndClarity(diamond.getOrigin(),
                diamond.getCarat_weight(), diamond.getColor(), diamond.getClarity());
        model.addAttribute("cuts", cuts);
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

    @GetMapping("/getPriceList")
    @ResponseBody
    public List<Float> getPriceList(@RequestParam String origin) {
        return diamondPriceListService.getCaratWeightsByOrigin(origin);
    }

    @GetMapping("/getPriceListByColor")
    @ResponseBody
    public List<String> getPriceListByColor(@RequestParam String origin, @RequestParam float carat_weight) {
        return diamondPriceListService.getColorsByOriginAndCaratWeight(origin, carat_weight);
    }

    @GetMapping("/getPriceListByClarity")
    @ResponseBody
    public List<String> getPriceListByClarity(@RequestParam String origin, @RequestParam float carat_weight,
                                              @RequestParam String color) {
        return diamondPriceListService.getClaritiesByOriginCaratWeightAndColor(origin, carat_weight, color);
    }

    @GetMapping("/getPriceListByCut")
    @ResponseBody
    public List<String> getPriceListByCut(@RequestParam String origin, @RequestParam float carat_weight,
                                          @RequestParam String color, @RequestParam String clarity) {
        return diamondPriceListService.getCutsByOriginCaratWeightColorAndClarity(origin, carat_weight, color, clarity);
    }

    @GetMapping("/getPriceListByDetails")
    @ResponseBody
    public DiamondPriceList getPriceListByDetails(@RequestParam String origin, @RequestParam float carat_weight,
                                                  @RequestParam String color, @RequestParam String clarity, @RequestParam String cut) {
        return diamondPriceListService.getPriceByDetails(origin, carat_weight, color, clarity, cut);
    }

    // Method to generate diamond code
    private String generateDiamondCode() {
        long count = diamondService.getAllDiamonds(PageRequest.of(0, 1)).getTotalElements();
        return String.format("DIA%03d", count + 1);
    }
}
