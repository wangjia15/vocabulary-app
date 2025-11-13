package com.example.vocabularyapp.service;

import com.example.vocabularyapp.entity.User;
import com.example.vocabularyapp.mapper.UserMapper;
import com.example.vocabularyapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(String username, String email, String password) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(username).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userMapper.findByEmail(email).isPresent()) {
            throw new RuntimeException("邮箱已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(username);
        user.setIsActive(true);

        userMapper.insert(user);
        return user;
    }

    public String login(String username, String password) {
        Optional<User> userOpt = userMapper.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("用户名或密码错误");
        }

        User user = userOpt.get();

        if (!user.getIsActive()) {
            throw new RuntimeException("账户已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    public Optional<User> findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    public User updateUser(Long userId, User updatedUser) {
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        user.setNickname(updatedUser.getNickname());
        user.setAvatar(updatedUser.getAvatar());

        userMapper.update(user);
        return user;
    }
}