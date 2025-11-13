package com.example.vocabularyapp.service;

import com.example.vocabularyapp.entity.Category;
import com.example.vocabularyapp.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public Category addCategory(Category category) {
        // 检查分类名称是否已存在
        Category existing = categoryMapper.findByNameAndUserId(category.getName(), category.getUserId());
        if (existing != null) {
            throw new RuntimeException("分类名称已存在");
        }

        categoryMapper.insert(category);
        return category;
    }

    public Category getCategoryById(Long id) {
        return categoryMapper.findById(id);
    }

    public List<Category> getUserCategories(Long userId) {
        return categoryMapper.findByUserId(userId);
    }

    public Category updateCategory(Category category, Long userId) {
        // 验证分类属于该用户
        Category existingCategory = categoryMapper.findById(category.getId());
        if (existingCategory == null || !existingCategory.getUserId().equals(userId)) {
            throw new RuntimeException("分类不存在或无权限修改");
        }

        categoryMapper.update(category);
        return category;
    }

    public void deleteCategory(Long categoryId, Long userId) {
        int result = categoryMapper.deleteById(categoryId, userId);
        if (result == 0) {
            throw new RuntimeException("分类不存在或无权限删除");
        }
    }
}