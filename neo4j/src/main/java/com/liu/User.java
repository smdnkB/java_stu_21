package com.liu;

import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Node("User")  // 标签名（不再是旧版的 @NodeEntity）
public class User {
    @Id @GeneratedValue  // ID 必须由 Neo4j 生成
    private Long id;

    @Property("name")  // 显式映射属性名（字段名与属性名不一致时需指定）
    private String name;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    private List<User> friends;

    // 必须有无参构造方法
    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    // Getter/Setter 省略
}
