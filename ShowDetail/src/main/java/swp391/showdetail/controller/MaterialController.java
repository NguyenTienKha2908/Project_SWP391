package swp391.showdetail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swp391.showdetail.Repository.DiamondPriceListRepository;
import swp391.showdetail.Repository.MaterialRepository;
import swp391.showdetail.models.DiamondPriceList;
import swp391.showdetail.models.Material;

import java.util.List;

@Controller
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping({"", "/"})
    public String showMaterialList(Model model) {
        List<Material> materials = materialRepository.findAll();
        model.addAttribute("materials", materials);
        return "materials";
    }
}
