package com.liu;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserRepository userRepository;

    @PostMapping("/addUser")
    public Object addUser(){
      /*  // 创建人物和关系
        User alice = new User();
        alice.setName("Alice");

        User bob = new User();
        bob.setName("Bob");
        alice.setFriends(List.of(bob));  // Alice -> Bob 是朋友

        // 保存（级联保存Bob）
        userRepository.save(alice);*/

        // 直接查询关系是否存在
        boolean exists = userRepository.existsFriendRelationship("Alice", "Bob");
        return ResponseEntity.ok();
    }
}
