package com.example.vocabularyapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRecord {
    private Long id;
    private Long userId;
    private Long wordId;
    private Boolean isCorrect;
    private LocalDateTime reviewTime;
    private Integer responseTime;

    // 关联字段
    private String english;
    private String chinese;
}