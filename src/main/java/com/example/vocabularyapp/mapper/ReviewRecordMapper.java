package com.example.vocabularyapp.mapper;

import com.example.vocabularyapp.entity.ReviewRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReviewRecordMapper {

    @Insert("INSERT INTO review_records(user_id, word_id, is_correct, review_time, response_time) " +
            "VALUES(#{userId}, #{wordId}, #{isCorrect}, #{reviewTime}, #{responseTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReviewRecord record);

    @Select("SELECT rr.*, w.english, w.chinese FROM review_records rr " +
            "JOIN words w ON rr.word_id = w.id " +
            "WHERE rr.user_id = #{userId} ORDER BY rr.review_time DESC")
    List<ReviewRecord> findByUserId(Long userId);

    @Select("SELECT rr.*, w.english, w.chinese FROM review_records rr " +
            "JOIN words w ON rr.word_id = w.id " +
            "WHERE rr.user_id = #{userId} AND rr.review_time >= #{startTime} AND rr.review_time <= #{endTime} " +
            "ORDER BY rr.review_time DESC")
    List<ReviewRecord> findByUserIdAndDateRange(@Param("userId") Long userId,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

    @Select("SELECT COUNT(*) FROM review_records WHERE user_id = #{userId} AND is_correct = #{isCorrect}")
    int countByUserIdAndCorrect(@Param("userId") Long userId, @Param("isCorrect") Boolean isCorrect);

    @Select("SELECT COUNT(*) FROM review_records WHERE user_id = #{userId} AND review_time >= #{date}")
    int countByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDateTime date);
}