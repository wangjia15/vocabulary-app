package com.example.vocabularyapp.mapper;

import com.example.vocabularyapp.entity.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WordMapper {

    @Insert("INSERT INTO words(english, chinese, pronunciation, example_sentence, category_id, user_id, difficulty_level) " +
            "VALUES(#{english}, #{chinese}, #{pronunciation}, #{exampleSentence}, #{categoryId}, #{userId}, #{difficultyLevel})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Word word);

    @Select("SELECT w.*, c.name as category_name FROM words w " +
            "LEFT JOIN categories c ON w.category_id = c.id " +
            "WHERE w.id = #{id}")
    Word findById(Long id);

    @Select("SELECT w.*, c.name as category_name FROM words w " +
            "LEFT JOIN categories c ON w.category_id = c.id " +
            "WHERE w.user_id = #{userId} OR w.user_id IS NULL " +
            "ORDER BY w.created_at DESC")
    List<Word> findByUserId(Long userId);

    @Select("SELECT w.*, c.name as category_name FROM words w " +
            "LEFT JOIN categories c ON w.category_id = c.id " +
            "WHERE w.category_id = #{categoryId} AND (w.user_id = #{userId} OR w.user_id IS NULL)")
    List<Word> findByCategory(@Param("categoryId") Long categoryId, @Param("userId") Long userId);

    @Select("SELECT w.*, c.name as category_name FROM words w " +
            "LEFT JOIN categories c ON w.category_id = c.id " +
            "WHERE (w.english LIKE CONCAT('%', #{keyword}, '%') OR w.chinese LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND (w.user_id = #{userId} OR w.user_id IS NULL)")
    List<Word> searchByKeyword(@Param("keyword") String keyword, @Param("userId") Long userId);

    @Update("UPDATE words SET english = #{english}, chinese = #{chinese}, pronunciation = #{pronunciation}, " +
            "example_sentence = #{exampleSentence}, category_id = #{categoryId}, difficulty_level = #{difficultyLevel} " +
            "WHERE id = #{id} AND user_id = #{userId}")
    int update(Word word);

    @Delete("DELETE FROM words WHERE id = #{id} AND user_id = #{userId}")
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM words WHERE user_id = #{userId}")
    int countByUserId(Long userId);
}