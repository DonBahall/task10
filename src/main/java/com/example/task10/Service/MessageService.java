package com.example.task10.Service;

import com.example.task10.Model.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private KafkaTemplate<String, MessageData> kafkaTemplate;

    public void sendMessage(String topic, MessageData message) {
        kafkaTemplate.send(topic, message);
    }

}
