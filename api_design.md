# 影视信息刮削与管理应用 - API 设计文档

本文档描述了应用所需的核心 API 接口及其业务逻辑流程。

## 一、 影视信息管理接口 (Admin API)

供后台管理系统使用，用于维护本地影视信息库。

### 1. `POST {{baseUrl}}/api/media-info`: 手动创建影视信息

当 TMDB 无法找到或信息不准时，管理员通过此接口手动录入一条完整的影视信息。

    ↓
接收包含完整影视信息的 DTO (包括标题、简介、类型、演职员等)
    ↓
数据合法性校验 (例如，标题不能为空)
    ↓
保存 `media_info` 主记录
    ↓
处理关联数据
    │
    ├─ **类型(Genre)**: 遍历 DTO 中的类型信息，查询 `genre` 表，如果不存在则创建，然后建立 `media_info` 与 `genre` 的关联。
    ├─ **演职员(Person/Credit)**: 遍历 DTO 中的演职员信息，查询 `person` 表，如果不存在则创建，然后创建 `media_credit` 记录，关联 `media_info`、`person` 并保存角色名、排序等信息。
    └─ **季/集(Season/Episode)**: 如果是电视剧，遍历 DTO 中的季和集信息，并依次存入 `tv_season` 和 `tv_episode` 表。
    ↓
返回成功响应，包含新创建的影视信息的 ID。

### 2. `PUT {{baseUrl}}/api/media-info/{id}`: 更新指定的影视信息

更新一条已存在的影视信息。

    ↓
接收包含待更新影视信息的 DTO 和路径参数 {id}
    ↓
通过 {id} 查询 `media_info` 记录，如果不存在则返回 404 Not Found
    ↓
更新 `media_info` 表中的基础字段 (标题、简介等)
    ↓
以“先删后增”或“差量更新”的逻辑处理关联数据
    │
    ├─ **类型(Genre)**: 对比新旧类型列表，删除不再关联的关系，添加新的关联。
    ├─ **演职员(Person/Credit)**: 删除所有旧的 `media_credit` 记录，然后根据新的 DTO 信息重新创建。
    └─ **季/集(Season/Episode)**: (逻辑较复杂) 对比新旧数据，更新、添加或删除 `tv_season` 和 `tv_episode` 记录。
    ↓
返回成功响应。

### 3. `DELETE {{baseUrl}}/api/media-info/{id}`: 删除指定的影视信息

删除一条影视信息及其所有相关联的数据。

    ↓
接收路径参数 {id}
    ↓
通过 {id} 查询 `media_info` 记录，记录下其关联的 `person` 和 `genre` 的 ID 列表，用于后续的垃圾回收检查。
    │
    ├─ **如果是电视剧**:
    │   ↓
    │   查询所有关联的 `tv_season` 记录
    │   ↓
    │   遍历 `tv_season`，删除其下所有 `tv_episode` 记录
    │   ↓
    │   删除 `tv_season` 记录本身
    │
    ↓
删除关联的 `media_credit` 记录 (演职员关系)
    ↓
删除关联的 `media_info_genres` 记录 (类型关系)
    ↓
删除 `media_info` 主记录
    ↓
**[可选] 垃圾回收 (Garbage Collection)**
    │
    ├─ **检查 Person**: 遍历之前记录的 `person` ID 列表，检查他们在 `media_credit` 表中是否还有其他引用。如果引用计数为 0，则可以删除该 `person` 记录。
    └─ **检查 Genre**: 遍历之前记录的 `genre` ID 列表，检查它们在 `media_info_genres` 表中是否还有其他引用。如果引用计数为 0，则可以删除该 `genre` 记录。
    ↓
返回成功响应。

### 4. `GET {{baseUrl}}/api/media-info/{id}`: 获取指定的影视信息详情

获取单条影视信息的完整数据，用于后台编辑页面展示。

    ↓
接收路径参数 {id}
    ↓
通过 {id} 查询 `media_info` 主记录
    ↓
联表查询 (JOIN) 或多次查询，获取所有关联数据
    │
    ├─ 查询关联的 `genre`
    ├─ 查询关联的 `media_credit` 及对应的 `person`
    └─ 如果是电视剧，查询所有 `tv_season` 及对应的 `tv_episode`
    ↓
将所有数据组装成一个完整的 DTO
    ↓
返回该 DTO。

### 5. `GET {{baseUrl}}/api/media-info`: 分页和筛选查询影视信息列表

用于后台管理页面的列表展示。

    ↓
接收分页参数 (page, size) 和筛选参数 (title, media_type, genre_id 等)
    ↓
根据筛选参数构建动态查询条件 (JPA Specification 或 QueryDSL)
    ↓
执行分页查询，获取 `media_info` 列表及总记录数
    ↓
将结果封装为分页对象
    ↓
返回分页对象。

---

## 二、 TMDB 同步接口 (Admin API)

供后台管理系统使用，从 TMDB 拉取数据并存入本地库。

### 1. `GET {{baseUrl}}/api/tmdb/search`: 从 TMDB 搜索影视

管理员输入关键词，此接口返回 TMDB 的搜索结果供选择。

    ↓
接收查询参数 (query, year, media_type)
    ↓
调用 TMDB 的搜索 API
    ↓
对 TMDB 返回的结果进行解析和简化，提取关键信息 (tmdb_id, title, release_date, poster_path)
    ↓
返回简化后的列表给前端，供管理员选择。

### 2. `POST {{baseUrl}}/api/tmdb/import`: 从 TMDB 导入指定的影视信息

管理员从搜索结果中选择一条后，调用此接口将其完整信息导入本地数据库。

    ↓
接收请求体，包含要导入的 `tmdb_id` 和 `media_type`
    ↓
检查本地数据库中该 `tmdb_id` 是否已存在，如果存在则返回错误或提示“已存在，是否覆盖？”
    ↓
调用 TMDB 的详情 API (如 `/movie/{tmdb_id}` 或 `/tv/{tmdb_id}`)，并追加请求演职员、图片等信息
    ↓
获取到 TMDB 返回的完整数据
    ↓
**执行与 `POST /api/media-info` 类似的保存逻辑**：
    │
    ├─ 将 TMDB 数据映射到 `media_info` 实体
    ├─ 将 TMDB 的 genres 数据映射到 `genre` 实体及关联关系
    ├─ 将 TMDB 的 credits 数据映射到 `person` 和 `media_credit` 实体
    └─ 如果是电视剧，递归获取所有季和集的信息，并映射到 `tv_season` 和 `tv_episode` 实体
    ↓
在一个数据库事务 (Transaction) 中，保存所有实体
    ↓
返回成功响应。

---

## 三、 客户端刮削接口 (Scraper API)

供客户端刮削器调用，为其提供元数据。

### 1. `GET {{baseUrl}}/api/scraper/search`: 搜索元数据

客户端刮削器根据文件名解析出的信息，调用此接口在 **本地数据库** 中查找匹配项。

    ↓
接收查询参数 (title, year)
    ↓
在本地的 `media_info` 表中执行模糊查询或精确查询
    │
    ├─ **查询策略**: 可以设计一套评分系统。例如，标题和年份都匹配的得分最高，只有标题匹配的得分次之。
    └─ **返回结果**: 根据策略，可以返回得分最高的唯一结果，或返回多个候选结果列表。
    ↓
返回一个或多个匹配的 `media_info` 简要信息 (包含本地数据库的 `id`, `title`, `release_date`)。

### 2. `GET {{baseUrl}}/api/scraper/metadata/{id}`: 获取完整的元数据

客户端刮削器在确定了影视的本地数据库 ID 后，调用此接口获取用于生成 `.info` 文件的所有数据。

    ↓
接收路径参数 {id} (这是我们自己数据库的 ID)
    ↓
**执行与 `GET /api/media-info/{id}` 完全相同的逻辑**:
    │
    ├─ 查询 `media_info` 主记录
    └─ 查询所有关联的 `genre`, `person`, `credit`, `season`, `episode`
    ↓
将所有数据组装成一个为刮削器优化的 DTO (字段名和结构可能需要调整以方便生成 XML)
    ↓
返回该 DTO。
