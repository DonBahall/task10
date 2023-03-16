package com.example.task10.Repos;

import com.example.task10.Model.MessageData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepos extends ElasticsearchRepository<MessageData,String> {
    List<MessageData> findAllByStatus(HttpStatus s);
}
