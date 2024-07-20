package com.example.demo.service;

import com.example.demo.entity.Blog;
import java.util.List;

public interface BlogService {
    List<Blog> getAllBlogs();
    Blog getBlogById(int id);
    void saveBlog(Blog blog);
    void deleteBlogById(int id);
}
