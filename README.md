# 背单词应用程序

基于Spring Boot、MyBatis和MySQL的背单词学习应用。

## 功能特性

### 用户管理
- 用户注册和登录
- JWT认证
- 用户资料管理

### 单词管理
- 添加、编辑、删除单词
- 单词分类管理
- 单词搜索
- 难度级别设置

### 学习功能
- 单词学习和复习
- 基于遗忘曲线的复习计划
- 学习记录统计
- 翻转卡片学习界面

### Web界面
- 响应式设计
- 交互式单词卡片
- 实时统计信息
- 搜索和筛选功能

## 技术栈

- **后端**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0
- **ORM**: MyBatis
- **安全**: Spring Security + JWT
- **前端**: HTML5 + Bootstrap 5 + JavaScript
- **模板引擎**: Thymeleaf

## 数据库设计

### 主要表结构

1. **users** - 用户表
2. **categories** - 单词分类表
3. **words** - 单词表
4. **learning_records** - 学习记录表
5. **review_records** - 复习记录表
6. **user_statistics** - 用户统计表

## 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置
创建MySQL数据库并执行 `schema.sql` 脚本：

```sql
-- 执行 src/main/resources/schema.sql
```

### 3. 修改配置
修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/vocabulary_db
    username: your_username
    password: your_password
```

### 4. 启动应用

```bash
# 使用Maven构建和运行
mvn clean spring-boot:run

# 或者先构建再运行
mvn clean package
java -jar target/vocabulary-app-1.0.0.jar
```

### 5. 访问应用
打开浏览器访问: http://localhost:8080

## API接口

### 认证相关
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录

### 单词管理
- `GET /api/words` - 获取用户单词列表
- `POST /api/words` - 添加新单词
- `PUT /api/words/{id}` - 更新单词
- `DELETE /api/words/{id}` - 删除单词
- `GET /api/words/search?keyword={keyword}` - 搜索单词

### 分类管理
- `GET /api/categories` - 获取分类列表
- `POST /api/categories` - 创建新分类
- `PUT /api/categories/{id}` - 更新分类
- `DELETE /api/categories/{id}` - 删除分类

### 学习功能
- `POST /api/learning/learn/{wordId}` - 学习单词
- `GET /api/learning/due` - 获取待复习单词
- `POST /api/learning/review/{wordId}` - 复习单词
- `GET /api/learning/stats` - 获取学习统计

### 公共接口
- `GET /api/public/words` - 获取公共单词
- `GET /api/public/categories` - 获取公共分类

## 项目结构

```
src/
├── main/
│   ├── java/com/example/vocabularyapp/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器
│   │   ├── dto/            # 数据传输对象
│   │   ├── entity/         # 实体类
│   │   ├── mapper/         # MyBatis映射器
│   │   ├── security/       # 安全相关
│   │   ├── service/        # 服务层
│   │   ├── util/           # 工具类
│   │   └── VocabularyApplication.java
│   └── resources/
│       ├── static/         # 静态资源
│       ├── templates/      # 模板文件
│       ├── application.yml # 配置文件
│       └── schema.sql      # 数据库脚本
└── test/                   # 测试代码
```

## 特色功能

### 遗忘曲线算法
应用实现了基于艾宾浩斯遗忘曲线的复习算法：
- 根据用户答题正确率自动调整复习间隔
- 6个掌握程度级别，从1小时到1个月的复习间隔
- 智能推荐待复习单词

### 交互式学习
- 翻转卡片式单词展示
- 点击查看释义
- 快速认识/不认识评价
- 实时统计反馈

### 用户体验
- 响应式设计，支持移动端
- 无需刷新页面的单页应用体验
- 本地存储用户登录状态
- 友好的错误提示

## 扩展建议

1. **语音功能**: 添加单词发音播放
2. **记忆图片**: 支持添加记忆图片
3. **社交功能**: 好友系统和单词分享
4. **学习计划**: 自定义学习计划和提醒
5. **数据导出**: 支持学习数据导出
6. **多语言支持**: 支持更多语言对

## 许可证

MIT License