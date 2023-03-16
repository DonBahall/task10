package com.example.task10.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.http.HttpStatus;


@Document(indexName = "my_index")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageData {
    @Id
    private String subject;
    private String content;
    private String email;
    private HttpStatus status;
    private String errorMessage;
}
