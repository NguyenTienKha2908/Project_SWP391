package swp391.showblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import swp391.showblog.service.BlogService;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

   @GetMapping("/blogs")
    public String listBlogs(Model model) {
        model.addAttribute("blogs", blogService.getAllBlogs());
        return "blogs";
    }


    @GetMapping("/blogs/{id}")
    public String blogDetail(@PathVariable int id, Model model) {
        model.addAttribute("blog", blogService.getBlogById(id));
        return "blogDetail";
    }
}
