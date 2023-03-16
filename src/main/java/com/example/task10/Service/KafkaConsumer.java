package com.example.task10.Service;

import com.example.task10.Model.MessageData;
import com.example.task10.Repos.MessageRepos;


import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    final EmailService service;
    final MessageRepos repos;

    public KafkaConsumer(EmailService service, MessageRepos repos) {
        this.service = service;
        this.repos = repos;
    }

    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(MessageData message) {
        if (message != null) {
            repos.save(message);
            if (service.sendSimpleEmail(message).isError()) {
                message.setStatus(HttpStatus.BAD_GATEWAY);
                repos.save(message);
            } else {
                message.setStatus(HttpStatus.OK);
                repos.save(message);
            }
        }
    }
}