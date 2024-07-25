package com.jewelry.KiraJewelry.controllers.Diamond;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;

import com.jewelry.KiraJewelry.models.DiamondPriceList;
import com.jewelry.KiraJewelry.service.Diamond.DiamondService;
import com.jewelry.KiraJewelry.service.DiamondPriceList.DiamondPriceListService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
// @RequestMapping("/diamondPriceList")
public class DiamondPriceListController {

    @Autowired
    private DiamondPriceListService diaPriceListService;

    @Autowired
    private DiamondService diamondService;

    @GetMapping("/diaPriceLists")
    public String viewDiaPriceListPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<DiamondPriceList> diaPriceListPage = diaPriceListService.getAllDiaPriceLists(pageable);
        model.addAttribute("diaPriceListPage", diaPriceListPage);
        return "employee/manager/DiamondPriceList/dia_price_lists";
    }

    @GetMapping("/showNewDiaPriceListForm")
    public String showNewDiaPriceListForm(Model model) {
        DiamondPriceList diaPriceList = new DiamondPriceList();
        model.addAttribute("diaPriceList", diaPriceList);
        model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());

        // Fetch and add origins to the model
        List<String> origins = diaPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        return "employee/manager/DiamondPriceList/new_dia_price_list";
    }

    @PostMapping("/saveDiaPriceList")
    public String saveDiaPriceList(@ModelAttribute("diaPriceList") @Valid DiamondPriceList diaPriceList,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());
            return "employee/manager/DiamondPriceList/new_dia_price_list";
        }
        diaPriceListService.saveDiaPriceList(diaPriceList);
        return "redirect:/diaPriceLists";
    }

    @GetMapping("/showFormForUpdateDiaPriceList/{id}")
    public String showFormForUpdateDiaPriceList(@PathVariable(value = "id") int id, Model model) {
        DiamondPriceList diaPriceList = diaPriceListService.getDiaPriceListById(id);
        model.addAttribute("diaPriceList", diaPriceList);

        prepareModelForUpdateForm(model, diaPriceList);

        return "employee/manager/DiamondPriceList/update_dia_price_list";
    }

    @PostMapping("/updateDiaPriceList")
    public String updateDiaPriceList(@ModelAttribute("diaPriceList") @Valid DiamondPriceList diaPriceList,
            @RequestParam("id") int id,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            prepareModelForUpdateForm(model, diaPriceList);
            return "employee/manager/DiamondPriceList/update_dia_price_list";
        }
        DiamondPriceList diamondPriceList = diaPriceListService.getDiaPriceListById(id);

        // Update fields in diamondPriceList from diaPriceList1 only if diaPriceList1
        // values are not null or empty
        if (diaPriceList.getCarat_Weight_From() != 0
                && !String.valueOf(diaPriceList.getCarat_Weight_From()).isEmpty()) {
            diamondPriceList.setCarat_Weight_From(diaPriceList.getCarat_Weight_From());
        }
        if (diaPriceList.getCarat_Weight_To() != 0 && !String.valueOf(diaPriceList.getCarat_Weight_To()).isEmpty()) {
            diamondPriceList.setCarat_Weight_To(diaPriceList.getCarat_Weight_To());
        }
        if (diaPriceList.getClarity() != null && !diaPriceList.getClarity().isEmpty()) {
            diamondPriceList.setClarity(diaPriceList.getClarity());
        }
        if (diaPriceList.getColor() != null && !diaPriceList.getColor().isEmpty()) {
            diamondPriceList.setColor(diaPriceList.getColor());
        }
        if (diaPriceList.getCut() != null && !diaPriceList.getCut().isEmpty()) {
            diamondPriceList.setCut(diaPriceList.getCut());
        }
        if (diaPriceList.getEff_Date() != null) {
            diamondPriceList.setEff_Date(diaPriceList.getEff_Date());
        }
        if (diaPriceList.getOrigin() != null && !diaPriceList.getOrigin().isEmpty()) {
            diamondPriceList.setOrigin(diaPriceList.getOrigin());
        }
        if (diaPriceList.getPrice() != 0 && !String.valueOf(diaPriceList.getPrice()).isEmpty()) {
            diamondPriceList.setPrice(diaPriceList.getPrice());
        }
        diaPriceListService.saveDiaPriceList(diamondPriceList);
        return "redirect:/diaPriceLists";
    }

    private void prepareModelForUpdateForm(Model model, DiamondPriceList diaPriceList) {
        model.addAttribute("Diamonds", diamondService.getAllActiveDiamonds());

        // Fetch necessary data for the dropdowns
        List<String> origins = diaPriceListService.getAllOrigins();
        model.addAttribute("origins", origins);

        List<String> colors = diaPriceListService.getColorsByOriginAndCaratWeightRange(
                diaPriceList.getOrigin(), diaPriceList.getCarat_Weight_From(), diaPriceList.getCarat_Weight_To());
        model.addAttribute("colors", colors);

        List<String> clarities = diaPriceListService.getClaritiesByOriginCaratWeightRangeAndColor(
                diaPriceList.getOrigin(), diaPriceList.getCarat_Weight_From(), diaPriceList.getCarat_Weight_To(),
                diaPriceList.getColor());
        model.addAttribute("clarities", clarities);

        List<String> cuts = diaPriceListService.getCutsByOriginCaratWeightRangeColorAndClarity(
                diaPriceList.getOrigin(), diaPriceList.getCarat_Weight_From(), diaPriceList.getCarat_Weight_To(),
                diaPriceList.getColor(), diaPriceList.getClarity());
        model.addAttribute("cuts", cuts);
    }

    @GetMapping("/deleteDiaPriceList/{id}")
    public String deleteDiaPriceList(@PathVariable(value = "id") int id) {
        diaPriceListService.deleteDiaPriceListById(id);
        return "redirect:/diaPriceLists";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/viewCustomerDiaPriceListPage")
    public String viewCustomerDiaPriceListPage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<DiamondPriceList> diaPriceListPage = diaPriceListService.getAllDiaPriceLists(pageable);
        model.addAttribute("diaPriceListPage", diaPriceListPage);
        LocalDateTime effDateTime = diaPriceListPage.getContent().get(0).getEff_Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedDate = effDateTime.format(formatter);
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("requestURI", request.getRequestURI());

        return "price/diamondPriceList";
    }

    @GetMapping("/getDiaPriceList")
    @ResponseBody
    public List<Double> getPriceList(@RequestParam String origin) {
        return diaPriceListService.getCaratWeightsByOrigin(origin);
    }

    @GetMapping("/getPriceListByColorRange")
    @ResponseBody
    public List<String> getPriceListByColorRange(
            @RequestParam String origin,
            @RequestParam float carat_weight_from,
            @RequestParam float carat_weight_to) {
        return diaPriceListService.getColorsByOriginAndCaratWeightRange(origin, carat_weight_from, carat_weight_to);
    }

    @GetMapping("/getPriceListByClarityRange")
    @ResponseBody
    public List<String> getPriceListByClarityRange(@RequestParam String origin, @RequestParam float carat_weight_from,
            @RequestParam float carat_weight_to, @RequestParam String color) {
        return diaPriceListService.getClaritiesByOriginCaratWeightRangeAndColor(origin, carat_weight_from,
                carat_weight_to, color);
    }

    @GetMapping("/getPriceListByCutRange")
    @ResponseBody
    public List<String> getPriceListByCutRange(@RequestParam String origin, @RequestParam float carat_weight_from,
            @RequestParam float carat_weight_to, @RequestParam String color, @RequestParam String clarity) {
        return diaPriceListService.getCutsByOriginCaratWeightRangeColorAndClarity(origin, carat_weight_from,
                carat_weight_to, color, clarity);
    }

    @GetMapping("/getDiaPriceListByDetails")
    @ResponseBody
    public DiamondPriceList getPriceListByDetails(@RequestParam String origin, @RequestParam double carat_weight,
            @RequestParam String color, @RequestParam String clarity, @RequestParam String cut) {
        return diaPriceListService.getPriceByDetails(origin, carat_weight, color, clarity, cut);
    }
}
