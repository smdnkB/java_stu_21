package com.liu;


import com.liu.kafka.service.KafkaProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        KafkaProducerService producerService = context.getBean(KafkaProducerService.class);

        // 新建 6个分区的 topic, 启动三个消费者消费 分别消费 01  23 45
// kafka-topics.sh --create --bootstrap-server localhost:9092 --topic user-topic --partitions 6 --replication-factor 1

        for (int j = 0; j < 100; j++) {

            for (int i = 0; i < 100; i++) {
                producerService.sendMessage("user-topic","111"+i);
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}