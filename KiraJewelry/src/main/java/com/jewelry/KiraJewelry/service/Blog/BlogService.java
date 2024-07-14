package com.jewelry.KiraJewelry.service.Blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Blog;
import com.jewelry.KiraJewelry.repository.BlogRepository;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(int id) {
        return blogRepository.findById(id).orElse(null);
    }

    public void saveBlog(Blog blog) {
        blogRepository.save(blog);
    }

    public void deleteBlogById(int id) {
        blogRepository.deleteById(id);
    }
}
