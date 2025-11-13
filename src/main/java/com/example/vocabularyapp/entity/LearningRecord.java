package com.example.vocabularyapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningRecord {
    private Long id;
    private Long userId;
    private Long wordId;
    private LocalDateTime learnedAt;
    private Integer reviewCount;
    private Integer correctCount;
    private Integer masteryLevel;
    private LocalDateTime nextReviewTime;

    // 关联字段
    private String english;
    private String chinese;
}