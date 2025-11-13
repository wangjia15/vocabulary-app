package com.example.vocabularyapp.service;

import com.example.vocabularyapp.entity.Word;
import com.example.vocabularyapp.mapper.WordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordMapper wordMapper;

    public Word addWord(Word word) {
        wordMapper.insert(word);
        return word;
    }

    public Word getWordById(Long id) {
        return wordMapper.findById(id);
    }

    public List<Word> getUserWords(Long userId) {
        return wordMapper.findByUserId(userId);
    }

    public List<Word> getWordsByCategory(Long categoryId, Long userId) {
        return wordMapper.findByCategory(categoryId, userId);
    }

    public List<Word> searchWords(String keyword, Long userId) {
        return wordMapper.searchByKeyword(keyword, userId);
    }

    public Word updateWord(Word word, Long userId) {
        // 验证单词属于该用户
        Word existingWord = wordMapper.findById(word.getId());
        if (existingWord == null || !existingWord.getUserId().equals(userId)) {
            throw new RuntimeException("单词不存在或无权限修改");
        }

        wordMapper.update(word);
        return word;
    }

    public void deleteWord(Long wordId, Long userId) {
        int result = wordMapper.deleteById(wordId, userId);
        if (result == 0) {
            throw new RuntimeException("单词不存在或无权限删除");
        }
    }

    public int getUserWordCount(Long userId) {
        return wordMapper.countByUserId(userId);
    }
}