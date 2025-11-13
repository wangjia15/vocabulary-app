-- 创建数据库
CREATE DATABASE IF NOT EXISTS vocabulary_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vocabulary_db;

-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- 单词分类表
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 单词表
CREATE TABLE words (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    english VARCHAR(100) NOT NULL,
    chinese VARCHAR(255) NOT NULL,
    pronunciation VARCHAR(255),
    example_sentence TEXT,
    category_id BIGINT,
    user_id BIGINT,
    difficulty_level INT DEFAULT 1 COMMENT '难度级别 1-5',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 学习记录表
CREATE TABLE learning_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    learned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    review_count INT DEFAULT 0,
    correct_count INT DEFAULT 0,
    mastery_level INT DEFAULT 0 COMMENT '掌握程度 0-5',
    next_review_time TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (word_id) REFERENCES words(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_word (user_id, word_id)
);

-- 复习记录表
CREATE TABLE review_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    review_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response_time INT COMMENT '答题时间（秒）',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (word_id) REFERENCES words(id) ON DELETE CASCADE
);

-- 用户统计表
CREATE TABLE user_statistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL,
    total_words_learned INT DEFAULT 0,
    total_reviews INT DEFAULT 0,
    correct_reviews INT DEFAULT 0,
    current_streak INT DEFAULT 0 COMMENT '连续学习天数',
    longest_streak INT DEFAULT 0,
    last_study_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 插入默认分类
INSERT INTO categories (name, description, user_id) VALUES
('基础词汇', '日常基础词汇', NULL),
('进阶词汇', '中高级词汇', NULL),
('专业词汇', '各专业领域词汇', NULL),
('高频词汇', '常用高频词汇', NULL);

-- 插入示例单词
INSERT INTO words (english, chinese, pronunciation, example_sentence, category_id, difficulty_level) VALUES
('hello', '你好', '[həˈləʊ]', 'Hello, how are you?', 1, 1),
('world', '世界', '[wɜːld]', 'Welcome to the world of programming.', 1, 1),
('study', '学习', '[ˈstʌdi]', 'I study English every day.', 1, 2),
('computer', '计算机', '[kəmˈpjuːtə]', 'I use a computer for work.', 2, 2),
('algorithm', '算法', '[ˈælɡərɪðəm]', 'This algorithm is very efficient.', 3, 4),
('database', '数据库', '[ˈdeɪtəbeɪs]', 'We store data in the database.', 3, 3);