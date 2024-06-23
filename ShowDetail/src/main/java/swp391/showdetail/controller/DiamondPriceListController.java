package swp391.showdetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swp391.showdetail.Repository.DiamondPriceListRepository;
import swp391.showdetail.models.DiamondPriceList;

import java.util.List;

@Controller
@RequestMapping("/diamondPriceList")
public class DiamondPriceListController {

    @Autowired
    private DiamondPriceListRepository diamondPriceListRepository;

    @GetMapping({"", "/"})
    public String showDiamondPriceList(Model model) {
        List<DiamondPriceList> diamondPriceList = diamondPriceListRepository.findAll();
        model.addAttribute("diamondPriceList", diamondPriceList);
        return "diamondPriceList"; // Tên tệp template phải là diamondPriceList.html
    }
}
