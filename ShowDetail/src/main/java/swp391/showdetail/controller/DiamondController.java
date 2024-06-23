package swp391.showdetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swp391.showdetail.Repository.DiamondRepository;
import swp391.showdetail.models.Diamond;

import java.util.List;

@Controller
@RequestMapping("/diamonds")
public class DiamondController {

    @Autowired
    private DiamondRepository diamondRepository;

    @GetMapping({"","/"})
    public String showDiamonds(Model model) {
        List<Diamond> diamonds = diamondRepository.findAll();
        model.addAttribute("diamonds", diamonds);
        return "diamonds";
    }

}
