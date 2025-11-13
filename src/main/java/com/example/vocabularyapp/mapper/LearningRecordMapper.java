package com.example.vocabularyapp.mapper;

import com.example.vocabularyapp.entity.LearningRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LearningRecordMapper {

    @Insert("INSERT INTO learning_records(user_id, word_id, learned_at, review_count, correct_count, mastery_level, next_review_time) " +
            "VALUES(#{userId}, #{wordId}, #{learnedAt}, #{reviewCount}, #{correctCount}, #{masteryLevel}, #{nextReviewTime}) " +
            "ON DUPLICATE KEY UPDATE " +
            "review_count = VALUES(review_count), " +
            "correct_count = VALUES(correct_count), " +
            "mastery_level = VALUES(mastery_level), " +
            "next_review_time = VALUES(next_review_time)")
    int upsert(LearningRecord record);

    @Select("SELECT lr.*, w.english, w.chinese FROM learning_records lr " +
            "JOIN words w ON lr.word_id = w.id " +
            "WHERE lr.user_id = #{userId} ORDER BY lr.learned_at DESC")
    List<LearningRecord> findByUserId(Long userId);

    @Select("SELECT lr.*, w.english, w.chinese FROM learning_records lr " +
            "JOIN words w ON lr.word_id = w.id " +
            "WHERE lr.user_id = #{userId} AND lr.next_review_time <= #{now} " +
            "ORDER BY lr.next_review_time ASC")
    List<LearningRecord> findDueWords(@Param("userId") Long userId, @Param("now") LocalDateTime now);

    @Select("SELECT lr.*, w.english, w.chinese FROM learning_records lr " +
            "JOIN words w ON lr.word_id = w.id " +
            "WHERE lr.user_id = #{userId} AND lr.word_id = #{wordId}")
    LearningRecord findByUserIdAndWordId(@Param("userId") Long userId, @Param("wordId") Long wordId);

    @Update("UPDATE learning_records SET review_count = review_count + 1, " +
            "correct_count = correct_count + #{correct}, " +
            "mastery_level = #{masteryLevel}, " +
            "next_review_time = #{nextReviewTime} " +
            "WHERE user_id = #{userId} AND word_id = #{wordId}")
    int updateReview(@Param("userId") Long userId, @Param("wordId") Long wordId,
                    @Param("correct") Integer correct, @Param("masteryLevel") Integer masteryLevel,
                    @Param("nextReviewTime") LocalDateTime nextReviewTime);

    @Select("SELECT COUNT(*) FROM learning_records WHERE user_id = #{userId}")
    int countByUserId(Long userId);
}