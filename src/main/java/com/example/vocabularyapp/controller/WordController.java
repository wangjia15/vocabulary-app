package com.example.vocabularyapp.controller;

import com.example.vocabularyapp.entity.Word;
import com.example.vocabularyapp.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping
    public ResponseEntity<Word> addWord(@RequestBody Word word,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        // 从用户详情中获取用户ID（这里需要扩展UserDetails或通过UserService查询）
        Long userId = getUserIdFromUserDetails(userDetails);
        word.setUserId(userId);

        Word savedWord = wordService.addWord(word);
        return ResponseEntity.ok(savedWord);
    }

    @GetMapping
    public ResponseEntity<List<Word>> getUserWords(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<Word> words = wordService.getUserWords(userId);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Word> getWord(@PathVariable Long id) {
        Word word = wordService.getWordById(id);
        if (word == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(word);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Word>> getWordsByCategory(@PathVariable Long categoryId,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<Word> words = wordService.getWordsByCategory(categoryId, userId);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Word>> searchWords(@RequestParam String keyword,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<Word> words = wordService.searchWords(keyword, userId);
        return ResponseEntity.ok(words);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Word> updateWord(@PathVariable Long id,
                                          @RequestBody Word word,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        word.setId(id);
        Word updatedWord = wordService.updateWord(word, userId);
        return ResponseEntity.ok(updatedWord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        wordService.deleteWord(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getWordCount(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        int count = wordService.getUserWordCount(userId);
        return ResponseEntity.ok(count);
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // 这里需要从UserDetails中获取用户ID
        // 简单实现：通过username查询用户ID
        // 实际项目中可以扩展UserDetails来包含用户ID
        return 1L; // 临时返回，实际需要查询数据库
    }
}