package com.example.vocabularyapp.service;

import com.example.vocabularyapp.entity.Word;
import com.example.vocabularyapp.mapper.WordMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    private WordMapper wordMapper;

    @InjectMocks
    private WordService wordService;

    @Test
    void testAddWord() {
        // 准备测试数据
        Word word = new Word();
        word.setEnglish("test");
        word.setChinese("测试");
        word.setUserId(1L);

        // 模拟Mapper行为
        when(wordMapper.insert(any(Word.class))).thenReturn(1);

        // 执行测试
        Word result = wordService.addWord(word);

        // 验证结果
        assertNotNull(result);
        assertEquals("test", result.getEnglish());
        assertEquals("测试", result.getChinese());
        verify(wordMapper, times(1)).insert(word);
    }

    @Test
    void testGetUserWords() {
        // 准备测试数据
        Long userId = 1L;
        List<Word> expectedWords = Arrays.asList(
                createWord("hello", "你好"),
                createWord("world", "世界")
        );

        // 模拟Mapper行为
        when(wordMapper.findByUserId(userId)).thenReturn(expectedWords);

        // 执行测试
        List<Word> result = wordService.getUserWords(userId);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("hello", result.get(0).getEnglish());
        verify(wordMapper, times(1)).findByUserId(userId);
    }

    @Test
    void testSearchWords() {
        // 准备测试数据
        String keyword = "test";
        Long userId = 1L;
        List<Word> expectedWords = Arrays.asList(createWord("test", "测试"));

        // 模拟Mapper行为
        when(wordMapper.searchByKeyword(keyword, userId)).thenReturn(expectedWords);

        // 执行测试
        List<Word> result = wordService.searchWords(keyword, userId);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getEnglish());
        verify(wordMapper, times(1)).searchByKeyword(keyword, userId);
    }

    private Word createWord(String english, String chinese) {
        Word word = new Word();
        word.setEnglish(english);
        word.setChinese(chinese);
        word.setUserId(1L);
        return word;
    }
}