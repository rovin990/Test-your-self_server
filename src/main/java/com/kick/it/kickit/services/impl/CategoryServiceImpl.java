package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.Utility.ImageUtil;
import com.kick.it.kickit.entities.Category;
import com.kick.it.kickit.entities.ImageData;
import com.kick.it.kickit.repository.CategoryRepository;
import com.kick.it.kickit.repository.ImageRepository;
import com.kick.it.kickit.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long categoryId) {

        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
         categoryRepository.deleteById(categoryId);
    }


}
