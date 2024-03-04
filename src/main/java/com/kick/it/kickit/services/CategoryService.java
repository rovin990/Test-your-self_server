package com.kick.it.kickit.services;

import com.kick.it.kickit.entities.Category;
import com.kick.it.kickit.entities.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    public Category saveCategory(Category category);

    public  Category getCategory(Long categoryId);

    public List<Category> getCategories();

    public Category updateCategory(Category category);

    public void deleteCategory(Long categoryId);


}
