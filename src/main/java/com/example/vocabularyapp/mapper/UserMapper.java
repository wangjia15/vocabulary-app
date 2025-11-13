package com.example.vocabularyapp.mapper;

import com.example.vocabularyapp.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(username, email, password, nickname, avatar, is_active) " +
            "VALUES(#{username}, #{email}, #{password}, #{nickname}, #{avatar}, #{isActive})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(String email);

    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> findById(Long id);

    @Update("UPDATE users SET nickname = #{nickname}, avatar = #{avatar} WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE users SET is_active = #{isActive} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("isActive") Boolean isActive);
}