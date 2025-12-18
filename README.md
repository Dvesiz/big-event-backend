# big-eventBackend

## 🚀 项目介绍

`big-eventBackend` 是一个为“大事件”应用提供后端服务的项目。它旨在为前端应用提供稳定、高效的 RESTful API 接口，支持用户管理、事件（文章）管理、分类管理等核心功能。本项目的目标是构建一个可扩展、易维护的后端服务，为各类事件发布、管理平台提供坚实的数据支撑。这是黑马程序员的大事件项目实战。

## ✨ 主要功能

*   **用户管理**:
    *   用户注册、登录 (支持 JWT 认证)
    *   获取、更新用户基本信息
    *   更新用户头像
    *   修改用户密码
*   **分类管理**:
    *   添加、删除、修改文章分类
    *   获取所有文章分类列表
*   **文章管理**:
    *   发布新文章
    *   获取文章列表 (支持分页、按分类筛选、按状态筛选)
    *   获取文章详情
    *   更新文章内容
    *   删除文章

## 🏗️ 软件架构

本项目基于 **Spring Boot** 框架开发，采用 **RESTful API** 风格，实现前后端分离。

*   **后端技术栈**:
    *   **核心框架**: Spring Boot 3.x
    *   **编程语言**: Java 17+
    *   **数据持久层**: MyBatis-Plus
    *   **数据库**: MySQL 8.x
    *   **认证授权**: JWT (JSON Web Token)
    *   **构建工具**: Maven
    *   **辅助工具**: Lombok (简化Java Bean开发)
    *   **API 文档**: Swagger/Knife4j (推荐集成，方便接口测试)

*   **架构分层**:
    *   **Controller**: 负责接收请求、调用 Service 层处理业务逻辑、返回响应。
    *   **Service**: 负责核心业务逻辑处理、事务管理。
    *   **Mapper/Repository**: 负责与数据库交互，进行数据持久化操作。
    *   **Entity/POJO**: 数据模型定义。

## ⚙️ 安装教程

请确保您的开发环境已安装以下软件：

1.  **Java Development Kit (JDK)**: 17 或更高版本
2.  **Maven**: 3.x 或更高版本
3.  **MySQL**: 8.x 或更高版本
4.  **Git**: 用于克隆项目

### 1. 数据库配置

1.  **创建数据库**:
    ```sql
    CREATE DATABASE `big_event` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```
2.  **配置数据库连接**:
    *   克隆项目到本地后，在 `src/main/resources/application.yml` (或 `application.properties`) 文件中配置您的数据库连接信息。
    *   示例 `application.yml` 配置：
        ```yaml
        spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: jdbc:mysql://localhost:3306/big_event?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
            username: your_mysql_username
            password: your_mysql_password
          # ... 其他 Spring Boot 配置
        ```
    *   **注意**: 项目启动时，MyBatis-Plus 会自动执行 SQL 脚本创建表结构（如果配置了）。

### 2. 后端项目启动

1.  **克隆仓库**:
    ```bash
    git clone https://gitee.com/your-username/big-eventBackend.git
    cd big-eventBackend
    ```
2.  **构建项目**:
    ```bash
    mvn clean install
    ```
3.  **运行项目**:
    ```bash
    mvn spring-boot:run
    ```
    或者，您可以打包成 JAR 后运行：
    ```bash
    java -jar target/big-eventBackend-0.0.1-SNAPSHOT.jar
    ```

项目默认会在 `http://localhost:8080` 端口启动。

## 🚀 使用说明 (API 文档)

项目启动后，您可以通过以下 API 接口与后端进行交互。建议使用 Postman、Swagger UI (如果集成) 或其他 API 测试工具进行测试。

**基础 URL**: `http://localhost:8080`

### 1. 用户模块

*   **注册用户**: `POST /user/register`
    *   **请求体**: `{"username": "testuser", "password": "password123"}`
*   **用户登录**: `POST /user/login`
    *   **请求体**: `{"username": "testuser", "password": "password123"}`
    *   **响应**: 成功返回 JWT Token
*   **获取用户信息**: `GET /user/info` (需要携带 JWT Token)
*   **更新用户信息**: `PUT /user/update` (需要携带 JWT Token)
    *   **请求体**: `{"id": 1, "nickname": "新昵称", "email": "new@example.com"}`
*   **更新用户头像**: `PATCH /user/updateAvatar` (需要携带 JWT Token)
    *   **请求体**: `{"avatarUrl": "http://new.avatar.url/image.jpg"}`
*   **更新用户密码**: `PATCH /user/updatePwd` (需要携带 JWT Token)
    *   **请求体**: `{"old_pwd": "old_password", "new_pwd": "new_password", "re_pwd": "new_password"}`

### 2. 分类模块

*   **添加分类**: `POST /category` (需要携带 JWT Token)
    *   **请求体**: `{"categoryName": "前端技术", "categoryAlias": "web_front"}`
*   **获取所有分类**: `GET /category` (需要携带 JWT Token)
*   **更新分类**: `PUT /category` (需要携带 JWT Token)
    *   **请求体**: `{"id": 1, "categoryName": "后端技术", "categoryAlias": "backend_tech"}`
*   **删除分类**: `DELETE /category/{id}` (需要携带 JWT Token)

### 3. 文章模块

*   **发布文章**: `POST /article` (需要携带 JWT Token)
    *   **请求体**: `{"title": "文章标题", "content": "文章内容...", "coverImg": "封面URL", "state": "已发布", "categoryId": 1}`
*   **获取文章列表**: `GET /article` (需要携带 JWT Token)
    *   **查询参数**: `pageNum=1`, `pageSize=10`, `categoryId=1`, `state=已发布`
*   **获取文章详情**: `GET /article/{id}` (需要携带 JWT Token)
*   **更新文章**: `PUT /article` (需要携带 JWT Token)
    *   **请求体**: `{"id": 1, "title": "更新后的标题", "content": "更新后的内容...", "coverImg": "新的封面URL", "state": "草稿", "categoryId": 2}`
*   **删除文章**: `DELETE /article/{id}` (需要携带 JWT Token)

## 🤝 参与贡献

我们非常欢迎您的贡献！如果您希望为本项目做出贡献，请遵循以下步骤：

1.  **Fork** 本仓库
2.  新建功能分支 (`git checkout -b feat_xxx`)
3.  提交您的代码 (`git commit -m 'feat: 添加xxx功能'`)
4.  将更改推送到远程分支 (`git push origin feat_xxx`)
5.  新建 **Pull Request**

## 📄 许可证

本项目采用 [MIT License](LICENSE) 开源。

## 📧 联系方式

如果您有任何问题、建议或合作意向，欢迎通过以下方式联系：

*   **作者**: [Dvesiz]
*   **邮箱**: [2207547110@qq.com]
