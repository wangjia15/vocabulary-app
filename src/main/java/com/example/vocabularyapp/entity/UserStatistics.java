package com.example.vocabularyapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistics {
    private Long id;
    private Long userId;
    private Integer totalWordsLearned;
    private Integer totalReviews;
    private Integer correctReviews;
    private Integer currentStreak;
    private Integer longestStreak;
    private LocalDate lastStudyDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}