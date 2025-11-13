package com.example.vocabularyapp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.vocabularyapp.mapper")
public class MyBatisConfig {
}