package com.liu.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(groupId = "inst-group",topicPartitions = @TopicPartition(
            topic = "user-topic",
            partitions = { "0","1",}  // 只消费分区 0
    ))
    public void receiveMessage(Object message) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("01: " + message);
    }

    @KafkaListener(groupId = "inst-group",topicPartitions = @TopicPartition(
            topic = "user-topic",
            partitions = { "2","3", }  // 只消费分区 0
    ))
    public void receiveMessage2(Object message) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("===23: " + message);
    }
    @KafkaListener(groupId = "inst-group",topicPartitions = @TopicPartition(
            topic = "user-topic",
            partitions = { "4","5" }  // 只消费分区 0
    ))
    public void receiveMessage3(Object message) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("================45: " + message);
    }
}
