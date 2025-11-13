package com.example.vocabularyapp.controller;

import com.example.vocabularyapp.entity.Category;
import com.example.vocabularyapp.entity.Word;
import com.example.vocabularyapp.service.CategoryService;
import com.example.vocabularyapp.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final CategoryService categoryService;
    private final WordService wordService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getPublicCategories() {
        List<Category> categories = categoryService.getUserCategories(null); // null表示获取公共分类
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/words")
    public ResponseEntity<List<Word>> getPublicWords() {
        List<Word> words = wordService.getUserWords(null); // null表示获取公共单词
        return ResponseEntity.ok(words);
    }

    @GetMapping("/words/{id}")
    public ResponseEntity<Word> getPublicWord(@PathVariable Long id) {
        Word word = wordService.getWordById(id);
        if (word == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(word);
    }

    @GetMapping("/words/category/{categoryId}")
    public ResponseEntity<List<Word>> getPublicWordsByCategory(@PathVariable Long categoryId) {
        List<Word> words = wordService.getWordsByCategory(categoryId, null); // null表示获取公共单词
        return ResponseEntity.ok(words);
    }

    @GetMapping("/words/search")
    public ResponseEntity<List<Word>> searchPublicWords(@RequestParam String keyword) {
        List<Word> words = wordService.searchWords(keyword, null); // null表示搜索公共单词
        return ResponseEntity.ok(words);
    }
}