package com.example.vocabularyapp.controller;

import com.example.vocabularyapp.entity.LearningRecord;
import com.example.vocabularyapp.entity.ReviewRecord;
import com.example.vocabularyapp.entity.Word;
import com.example.vocabularyapp.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learning")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    @PostMapping("/learn/{wordId}")
    public ResponseEntity<Word> learnWord(@PathVariable Long wordId,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        Word word = learningService.learnWord(userId, wordId);
        return ResponseEntity.ok(word);
    }

    @GetMapping("/records")
    public ResponseEntity<List<LearningRecord>> getLearningRecords(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<LearningRecord> records = learningService.getUserLearningRecords(userId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/due")
    public ResponseEntity<List<LearningRecord>> getDueWords(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<LearningRecord> dueWords = learningService.getDueWords(userId);
        return ResponseEntity.ok(dueWords);
    }

    @PostMapping("/review/{wordId}")
    public ResponseEntity<ReviewRecord> reviewWord(@PathVariable Long wordId,
                                                  @RequestBody Map<String, Object> request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        boolean isCorrect = (Boolean) request.get("isCorrect");
        int responseTime = (Integer) request.get("responseTime");

        ReviewRecord reviewRecord = learningService.reviewWord(userId, wordId, isCorrect, responseTime);
        return ResponseEntity.ok(reviewRecord);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewRecord>> getReviewRecords(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        List<ReviewRecord> records = learningService.getUserReviewRecords(userId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getLearningStats(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);

        int todayReviews = learningService.getTodayReviewCount(userId);
        int correctRate = learningService.getCorrectReviewRate(userId);
        int dueWordsCount = learningService.getDueWords(userId).size();

        Map<String, Object> stats = Map.of(
                "todayReviews", todayReviews,
                "correctRate", correctRate,
                "dueWordsCount", dueWordsCount
        );

        return ResponseEntity.ok(stats);
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        return 1L; // 临时返回，实际需要查询数据库
    }
}