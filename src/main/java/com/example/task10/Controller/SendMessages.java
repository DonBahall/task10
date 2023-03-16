package com.example.task10.Controller;

import com.example.task10.Model.MessageData;

import com.example.task10.Service.MessageService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessages {

    final MessageService service;

    public SendMessages(MessageService service) {
        this.service = service;
    }

    @SneakyThrows
    @PostMapping("/")
    private MessageData send(){
        MessageData data = new MessageData("its_me","hello","dimachar2004@gmail.com", HttpStatus.ACCEPTED,"");
        service.sendMessage("my-topic", data);
        return data;
    }

}
