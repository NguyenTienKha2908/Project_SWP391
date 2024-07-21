package com.jewelry.KiraJewelry.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jewelry.KiraJewelry.models.Category;
import com.jewelry.KiraJewelry.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public String getCateNameById(int catergory_Id) {
        return categoryRepository.getCateNameById(catergory_Id);
    }
    
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
    }
}
