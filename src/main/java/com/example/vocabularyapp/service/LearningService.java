package com.example.vocabularyapp.service;

import com.example.vocabularyapp.entity.LearningRecord;
import com.example.vocabularyapp.entity.ReviewRecord;
import com.example.vocabularyapp.entity.Word;
import com.example.vocabularyapp.mapper.LearningRecordMapper;
import com.example.vocabularyapp.mapper.ReviewRecordMapper;
import com.example.vocabularyapp.mapper.WordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final LearningRecordMapper learningRecordMapper;
    private final ReviewRecordMapper reviewRecordMapper;
    private final WordMapper wordMapper;

    public Word learnWord(Long userId, Long wordId) {
        Word word = wordMapper.findById(wordId);
        if (word == null) {
            throw new RuntimeException("单词不存在");
        }

        // 检查是否已经学习过
        LearningRecord existingRecord = learningRecordMapper.findByUserIdAndWordId(userId, wordId);
        if (existingRecord == null) {
            // 创建学习记录
            LearningRecord record = new LearningRecord();
            record.setUserId(userId);
            record.setWordId(wordId);
            record.setLearnedAt(LocalDateTime.now());
            record.setReviewCount(0);
            record.setCorrectCount(0);
            record.setMasteryLevel(0);
            record.setNextReviewTime(LocalDateTime.now().plusDays(1)); // 第一次复习时间

            learningRecordMapper.upsert(record);
        }

        return word;
    }

    public List<LearningRecord> getUserLearningRecords(Long userId) {
        return learningRecordMapper.findByUserId(userId);
    }

    public List<LearningRecord> getDueWords(Long userId) {
        return learningRecordMapper.findDueWords(userId, LocalDateTime.now());
    }

    public ReviewRecord reviewWord(Long userId, Long wordId, boolean isCorrect, int responseTime) {
        // 记录复习结果
        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setUserId(userId);
        reviewRecord.setWordId(wordId);
        reviewRecord.setIsCorrect(isCorrect);
        reviewRecord.setReviewTime(LocalDateTime.now());
        reviewRecord.setResponseTime(responseTime);

        reviewRecordMapper.insert(reviewRecord);

        // 更新学习记录
        LearningRecord existingRecord = learningRecordMapper.findByUserIdAndWordId(userId, wordId);
        if (existingRecord != null) {
            int newMasteryLevel = calculateNewMasteryLevel(existingRecord.getMasteryLevel(), isCorrect);
            LocalDateTime nextReviewTime = calculateNextReviewTime(newMasteryLevel);

            learningRecordMapper.updateReview(
                    userId, wordId,
                    isCorrect ? 1 : 0,
                    newMasteryLevel,
                    nextReviewTime
            );
        }

        return reviewRecord;
    }

    private int calculateNewMasteryLevel(int currentLevel, boolean isCorrect) {
        if (isCorrect) {
            return Math.min(5, currentLevel + 1);
        } else {
            return Math.max(0, currentLevel - 1);
        }
    }

    private LocalDateTime calculateNextReviewTime(int masteryLevel) {
        LocalDateTime now = LocalDateTime.now();
        return switch (masteryLevel) {
            case 0 -> now.plusHours(1);    // 1小时后复习
            case 1 -> now.plusHours(6);    // 6小时后复习
            case 2 -> now.plusDays(1);     // 1天后复习
            case 3 -> now.plusDays(3);     // 3天后复习
            case 4 -> now.plusDays(7);     // 1周后复习
            case 5 -> now.plusDays(30);    // 1月后复习
            default -> now.plusDays(1);
        };
    }

    public List<ReviewRecord> getUserReviewRecords(Long userId) {
        return reviewRecordMapper.findByUserId(userId);
    }

    public int getTodayReviewCount(Long userId) {
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return reviewRecordMapper.countByUserIdAndDate(userId, todayStart);
    }

    public int getCorrectReviewRate(Long userId) {
        int totalReviews = reviewRecordMapper.countByUserIdAndCorrect(userId, true) +
                          reviewRecordMapper.countByUserIdAndCorrect(userId, false);
        if (totalReviews == 0) return 0;

        int correctReviews = reviewRecordMapper.countByUserIdAndCorrect(userId, true);
        return (correctReviews * 100) / totalReviews;
    }
}