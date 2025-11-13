package com.example.vocabularyapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    private Long id;
    private String english;
    private String chinese;
    private String pronunciation;
    private String exampleSentence;
    private Long categoryId;
    private Long userId;
    private Integer difficultyLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 关联字段
    private String categoryName;
}