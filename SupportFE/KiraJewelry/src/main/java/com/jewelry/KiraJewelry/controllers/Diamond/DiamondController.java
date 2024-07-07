package com.jewelry.KiraJewelry.controllers.Diamond;

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

import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.models.DiamondPriceList;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.Diamond.DiamondService;
import com.jewelry.KiraJewelry.service.DiamondPriceList.DiamondPriceListService;

import jakarta.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private DiamondPriceListService diamondPriceListService;

    @Autowired
    ImageService imageService;

    @GetMapping("/diamonds")
    // public String viewDiamondsPage(Model model,
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "20") int size,
    // @RequestParam(value = "message", required = false) String message) {
    // Pageable pageable = PageRequest.of(page, size);
    // Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

    // // Initialize list to hold image URLs for each material
    // List<String> imagesByDiamond = new ArrayList<>();

    // for (Diamond diamond : diamondPage) {
    // try {
    // String imageUrl =
    // imageService.getImgByMaterialID(String.valueOf(diamond.getDia_Id()));
    // imagesByDiamond.add(imageUrl);
    // System.out.println(imageUrl);
    // } catch (IOException ex) {
    // ex.printStackTrace();
    // }

    // }

    // if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
    // return "redirect:/diamonds?page=" + (diamondPage.getTotalPages() - 1) +
    // "&size=" + size;
    // }

    // model.addAttribute("diamondPage", diamondPage);
    // model.addAttribute("message", message);
    // return "employee/manager/Diamond/diamonds";
    // }

    // @GetMapping("/diamonds")
    public String viewDiamondsPage(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(value = "message", required = false) String message) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

        // Initialize a list to hold image URLs
        List<String> imagesByDiamond = new ArrayList<>();

        for (Diamond diamond : diamondPage) {
            try {
                String imageUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));
                imagesByDiamond.add(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
                imagesByDiamond.add(null); // Add null if there is an error fetching the image
            }
        }

        if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
            return "redirect:/diamonds?page=" + (diamondPage.getTotalPages() - 1) + "&size=" + size;
        }

        model.addAttribute("diamondPage", diamondPage);
        model.addAttribute("imagesByDiamond", imagesByDiamond);
        model.addAttribute("message", message);
        return "employee/manager/Diamond/diamonds";
    }

    // Method to generate diamond code
    private String generateDiamondCode() {
        long count = diamondService.getAllDiamonds(PageRequest.of(0, 1)).getTotalElements();
        return String.format("DIA%03d", count + 1);
    }

    @GetMapping("/showNewDiamondForm")
    public String showNewDiamondForm(Model model) {
        Diamond diamond = new Diamond();
        diamond.setDia_Code(generateDiamondCode());
        ;
        diamond.setO_Price(0.0); // Set default O Price
        model.addAttribute("diamond", diamond);

        List<String> origins = diamondPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);
        return "employee/manager/Diamond/new_diamond";
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
            return "employee/manager/Diamond/new_diamond";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                // String url = imageService.upload(imgFile);
                // diamond.setImgUrl(url);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Diamond/new_diamond";
            }
        }

        if (diamond.getDia_Id() == 0) { // Only generate a new code if the diamond is new
            diamond.setDia_Code(generateDiamondCode());
        }
        diamond.setStatus(true); // Set status to active by default
        diamond.setO_Price(0.0); // Ensure O Price is set to 0

        DiamondPriceList priceList = diamondPriceListService.getPriceByDetails(
                diamond.getOrigin(), diamond.getCarat_Weight(), diamond.getColor(),
                diamond.getClarity(), diamond.getCut());
        if (priceList != null) {
            diamond.setQ_Price(priceList.getPrice());
        } else {
            model.addAttribute("errorMessage", "No matching price found for the provided diamond details.");
            return "employee/manager/Diamond/new_diamond";
        }

        diamondService.saveDiamond(diamond);
        redirectAttributes.addAttribute("message",
                "Diamond " + diamond.getDia_Code() + " has been added successfully.");
        return "redirect:/diamonds?";
    }

    @GetMapping("/showFormForUpdateDiamond/{id}")
    public String showFormForUpdateDiamond(@PathVariable(value = "id") int id, Model model) {
        Diamond diamond = diamondService.getDiamondById(id);
        model.addAttribute("diamond", diamond);

        List<String> origins = diamondPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        List<String> colors = diamondPriceListService.getColorsByOriginAndCaratWeight(diamond.getOrigin(),
                diamond.getCarat_Weight());
        model.addAttribute("colors", colors);

        List<String> clarities = diamondPriceListService.getClaritiesByOriginCaratWeightAndColor(diamond.getOrigin(),
                diamond.getCarat_Weight(), diamond.getColor());
        model.addAttribute("clarities", clarities);

        List<String> cuts = diamondPriceListService.getCutsByOriginCaratWeightColorAndClarity(diamond.getOrigin(),
                diamond.getCarat_Weight(), diamond.getColor(), diamond.getClarity());
        model.addAttribute("cuts", cuts);
        return "employee/manager/Diamond/update_diamond";
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

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                // String url = imageService.upload(imgFile);
                // diamond.setImgUrl(url);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Diamond/new_diamond";
            }
        }

        DiamondPriceList priceList = diamondPriceListService.getPriceByDetails(
                diamond.getOrigin(), diamond.getCarat_Weight(), diamond.getColor(),
                diamond.getClarity(), diamond.getCut());
        if (priceList != null) {
            diamond.setQ_Price(priceList.getPrice());
            System.out.println("Price found: " + priceList.getPrice()); // Logging the price
        } else {
            model.addAttribute("errorMessage", "No matching price found for the provided diamond details.");
            prepareModelForUpdateForm(model, diamond);
            return "Diamond/update_diamond";
        }

        System.out.println("Updating diamond: " + diamond); // Logging the diamond details
        diamondService.saveDiamond(diamond);
        System.out.println("Diamond saved successfully");

        redirectAttributes.addAttribute("message",
                "Diamond " + diamond.getDia_Code() + " has been updated successfully.");
        return "employee/manager/Diamond/update_diamond";
    }

    // private void handleImageFile(Diamond diamond, MultipartFile imgFile) throws
    // IOException {
    // if (imgFile != null && !imgFile.isEmpty()) {
    // String url = imageService.upload(imgFile);
    // diamond.setImgUrl(url);
    // }
    // }

    private void prepareModelForUpdateForm(Model model, Diamond diamond) {
        List<String> origins = diamondPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        List<String> colors = diamondPriceListService.getColorsByOriginAndCaratWeight(diamond.getOrigin(),
                diamond.getCarat_Weight());
        model.addAttribute("colors", colors);

        List<String> clarities = diamondPriceListService.getClaritiesByOriginCaratWeightAndColor(diamond.getOrigin(),
                diamond.getCarat_Weight(), diamond.getColor());
        model.addAttribute("clarities", clarities);

        List<String> cuts = diamondPriceListService.getCutsByOriginCaratWeightColorAndClarity(diamond.getOrigin(),
                diamond.getCarat_Weight(), diamond.getColor(), diamond.getClarity());
        model.addAttribute("cuts", cuts);
    }

    @GetMapping("/deactivateDiamond/{id}")
    public String deactivateDiamond(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        try {
            Diamond diamond = diamondService.getDiamondById(id);
            if (diamond == null) {
                redirectAttributes.addFlashAttribute("message", "Diamond not found.");
            } else {
                diamond.setStatus(false); // Set status to inactive
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
                diamond.setStatus(true); // Set status to active
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
    public List<Double> getPriceList(@RequestParam String origin) {
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
    public DiamondPriceList getPriceListByDetails(@RequestParam String origin, @RequestParam double carat_weight,
            @RequestParam String color, @RequestParam String clarity, @RequestParam String cut) {
        return diamondPriceListService.getPriceByDetails(origin, carat_weight, color, clarity, cut);
    }

    @GetMapping("/customerDiamondsPage")
    public String viewCustomerDiamondsPage(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(value = "message", required = false) String message) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Diamond> diamondPage = diamondService.getAllDiamonds(pageable);

        // Initialize a list to hold image URLs
        List<String> imagesByDiamond = new ArrayList<>();

        for (Diamond diamond : diamondPage) {
            try {
                String imageUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));
                imagesByDiamond.add(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
                imagesByDiamond.add(null); // Add null if there is an error fetching the image
            }
        }

        if (page >= diamondPage.getTotalPages() && diamondPage.getTotalPages() > 0) {
            return "redirect:/customerDiamondsPage?page=" + (diamondPage.getTotalPages() - 1) + "&size=" + size;
        }

        model.addAttribute("diamondPage", diamondPage);
        model.addAttribute("imagesByDiamond", imagesByDiamond);
        model.addAttribute("message", message);
        return "price/customerDiamondsPage";
    }
}
