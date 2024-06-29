package com.example.CustomerView.service;

import com.example.CustomerView.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void saveCategory(Category category);
    void deleteCategoryById(int id);
}
