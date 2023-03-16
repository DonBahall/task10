package com.example.task10;

import com.example.task10.Model.MessageData;
import com.example.task10.Repos.MessageRepos;
import com.example.task10.Service.EmailService;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class Task10ApplicationTests {
    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    MessageRepos repos;

    MessageData data = new MessageData("Test Subject","Test Body",
            "test@example.com", HttpStatus.ACCEPTED,"");

    @Before
    public void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        emailService = new EmailService(javaMailSender,repos);
    }

    @Test
    public void testSendEmail()  {
        emailService.sendSimpleEmail(data);
        AssertionsForClassTypes.assertThat(emailService.sendSimpleEmail(data)).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void testSendEmailFailure() {
        if(emailService.sendSimpleEmail(data).isError()){
            AssertionsForClassTypes.assertThat(data.getErrorMessage()).isNotEmpty();
        }
    }
}
