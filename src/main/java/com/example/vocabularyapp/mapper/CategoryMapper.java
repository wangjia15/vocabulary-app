package com.example.vocabularyapp.mapper;

import com.example.vocabularyapp.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("INSERT INTO categories(name, description, user_id) VALUES(#{name}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    @Select("SELECT * FROM categories WHERE id = #{id}")
    Category findById(Long id);

    @Select("SELECT * FROM categories WHERE user_id = #{userId} OR user_id IS NULL ORDER BY created_at")
    List<Category> findByUserId(Long userId);

    @Select("SELECT * FROM categories WHERE name = #{name} AND user_id = #{userId}")
    Category findByNameAndUserId(@Param("name") String name, @Param("userId") Long userId);

    @Update("UPDATE categories SET name = #{name}, description = #{description} WHERE id = #{id} AND user_id = #{userId}")
    int update(Category category);

    @Delete("DELETE FROM categories WHERE id = #{id} AND user_id = #{userId}")
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}