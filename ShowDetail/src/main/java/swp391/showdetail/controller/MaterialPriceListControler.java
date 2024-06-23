package swp391.showdetail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swp391.showdetail.DTO.MaterialDto;
import swp391.showdetail.Repository.MaterialPriceListRepository;
import swp391.showdetail.Repository.MaterialRepository;
import swp391.showdetail.Service.MaterialService;
import swp391.showdetail.models.Material;

import java.util.List;

@Controller
@RequestMapping("/materialPriceList")
public class MaterialPriceListControler {


    @Autowired
    private MaterialService materialService;

    @GetMapping({"", "/"})
    public String showMaterialList(Model model) {
        List<MaterialDto> materialDto = materialService.findMaterialDetails();
        model.addAttribute("materialsPriceList", materialDto);
        return "materialPriceList";
    }

}
