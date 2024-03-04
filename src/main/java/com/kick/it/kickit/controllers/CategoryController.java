package com.kick.it.kickit.controllers;

import com.kick.it.kickit.entities.Category;
import com.kick.it.kickit.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/{filter}") // fetch or get by titles
    public ResponseEntity<List<Category>> getCategories(@PathVariable String filter){
        ResponseEntity response=null;
        try{
            List<Category> localCategories =categoryService.getCategories().stream().filter(item->{
                if(filter.equalsIgnoreCase("All"))return true;
                else if(item.getTitle().equalsIgnoreCase(filter)){
                    return true;
                }
                else {
                    return false;
                }
            }).toList();

            response=ResponseEntity.status(HttpStatus.OK).body(localCategories);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping //save
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        ResponseEntity response= null;
        try{
            Category local=categoryService.saveCategory(category);
            response=ResponseEntity.status(HttpStatus.CREATED).body(local);
        }catch (Exception e){
            e.getMessage();
        }
        return response;
    }



    @PutMapping //update
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        ResponseEntity response= null;
        try{
            Category local=categoryService.updateCategory(category);
            response=ResponseEntity.status(HttpStatus.OK).body(local);
        }catch (Exception e){
            e.getMessage();
        }
        return response;
    }

    @DeleteMapping("/{cId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long cId){
        ResponseEntity response= null;
        try{
            categoryService.deleteCategory(cId);
            response=ResponseEntity.status(HttpStatus.OK).body("Successfully deleted category id : "+cId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
