package com.modeul.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Category;
import com.modeul.web.service.CategoryService;

@RestController("apiCategoryController")
@RequestMapping("/api/category")
public class CategoryController {
    
    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> getList(){
        return service.getList();
    }

    @PostMapping
    public String addCategory(@RequestBody Category category){
        service.addCategory(category);
        return "insert";
    }

    @PutMapping
    public String updateCategory(@RequestBody Category category){
        service.updateCategory(category);
        return "update";
    }

    @DeleteMapping String delCategory(Long id){
        service.delCategory(id);
        return "delete";
    }
}
