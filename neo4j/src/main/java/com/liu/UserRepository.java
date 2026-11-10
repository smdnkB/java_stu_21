package com.liu;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    // 查询某人的所有朋友
    @Query("MATCH (p:User)-[:FRIEND_OF]->(friend:User) WHERE p.name = $name RETURN friend")
    List<User> findFriendsByName(String name);

    // 查询两人是否存在朋友关系
    @Query("MATCH (a:User)-[:FRIENDS_WITH]->(b:User) WHERE a.name = $name1 AND b.name = $name2 RETURN COUNT(a) > 0")
    boolean existsFriendRelationship(@Param("name1") String name1,@Param("name2")  String name2);
}
