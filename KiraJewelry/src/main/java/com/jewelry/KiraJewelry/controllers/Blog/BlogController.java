package com.jewelry.KiraJewelry.controllers.Blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.models.Blog;
import com.jewelry.KiraJewelry.service.Blog.BlogService;

import jakarta.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public String viewBlogsPage(Model model) {
        List<Blog> listBlogs = blogService.getAllBlogs();
        model.addAttribute("listBlogs", listBlogs);
        return "employee/manager/Blog/blogs";
    }

    @GetMapping("/showNewBlogForm")
    public String showNewBlogForm(Model model) {
        Blog blog = new Blog();
        model.addAttribute("blog", blog);
        return "employee/manager/Blog/new_blog";
    }

    @PostMapping("/saveBlog")
    public String saveBlog(@ModelAttribute("blog") @Valid Blog blog,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/manager/Blog/new_blog";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                blog.setImageData(imageBytes);
                blog.setImg_Url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Blog/new_blog";
            }
        } else if (blog.getImg_Url() != null && !blog.getImg_Url().isEmpty()) {
            try {
                @SuppressWarnings("deprecation")
                URL url = new URL(blog.getImg_Url());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (InputStream is = url.openStream()) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, n);
                    }
                }
                byte[] imageBytes = baos.toByteArray();
                blog.setImageData(imageBytes);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Could not retrieve image from URL: " + e.getMessage());
                return "employee/manager/Blog/new_blog";
            }
        }

        blogService.saveBlog(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/showFormForUpdateBlog/{id}")
    public String showFormForUpdateBlog(@PathVariable(value = "id") int id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "employee/manager/Blog/update_blog";
    }

    @PostMapping("/updateBlog")
    public String updateBlog(@ModelAttribute("blog") @Valid Blog blog,
            BindingResult result,
            @RequestParam("imgFile") MultipartFile imgFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/manager/Blog/update_blog";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                byte[] imageBytes = imgFile.getBytes();
                blog.setImageData(imageBytes);
                blog.setImg_Url(imgFile.getOriginalFilename());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not save image file: " + e.getMessage());
                return "employee/manager/Blog/update_blog";
            }
        } else if (blog.getImg_Url() != null && !blog.getImg_Url().isEmpty()) {
            if (blog.getImageData() == null || blog.getImageData().length == 0) {
                try {
                    @SuppressWarnings("deprecation")
                    URL url = new URL(blog.getImg_Url());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try (InputStream is = url.openStream()) {
                        byte[] buffer = new byte[1024];
                        int n;
                        while ((n = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, n);
                        }
                    }
                    byte[] imageBytes = baos.toByteArray();
                    blog.setImageData(imageBytes);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Could not retrieve image from URL: " + e.getMessage());
                    return "employee/manager/Blog/update_blog";
                }
            }
        }

        blogService.saveBlog(blog);
        return "redirect:/blogs";
    }

    @PostMapping("/deactivateBlog/{id}")
    public String deactivateBlog(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        Blog blog = blogService.getBlogById(id);
        if (blog != null) {
            blog.setStatus(false);
            blogService.saveBlog(blog);
            redirectAttributes.addFlashAttribute("message", "Blog deactivated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Blog not found.");
        }
        return "redirect:/blogs";
    }

    @PostMapping("/activateBlog/{id}")
    public String activateBlog(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        Blog blog = blogService.getBlogById(id);
        if (blog != null) {
            blog.setStatus(true);
            blogService.saveBlog(blog);
            redirectAttributes.addFlashAttribute("message", "Blog activated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Blog not found.");
        }
        return "redirect:/blogs";
    }

    @PostMapping("/deleteBlog/{id}")
    public String deleteBlog(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlogById(id);
        redirectAttributes.addFlashAttribute("message", "Blog deleted successfully.");
        return "redirect:/blogs";
    }
}
