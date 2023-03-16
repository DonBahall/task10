package com.example.task10.Service;

import com.example.task10.Model.MessageData;
import com.example.task10.Repos.MessageRepos;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    final MessageRepos repos;

    public EmailService(JavaMailSender javaMailSender, MessageRepos repos) {
        this.javaMailSender = javaMailSender;
        this.repos = repos;
    }

    public HttpStatus sendSimpleEmail(MessageData data) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(data.getEmail());
            simpleMailMessage.setSubject(data.getSubject());
            simpleMailMessage.setText(data.getContent());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            MessageData data2 = repos.findById(data.getSubject()).orElseThrow();
            data2.setErrorMessage(e.getMessage());
            e.printStackTrace();
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    @SneakyThrows
    @Scheduled(cron = "0 */5 * * * *")
    public void sendEmails() {
        List<MessageData> messages = getMessageWithErrorMessage();
        for (MessageData message : messages) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(message.getEmail());
            messageHelper.setSubject(message.getSubject());
            messageHelper.setText(message.getContent(), true);
            javaMailSender.send(mimeMessage);
            markMessageAsSent(message.getSubject());
        }
    }

    private List<MessageData> getMessageWithErrorMessage() {
        return repos.findAllByStatus(HttpStatus.BAD_GATEWAY);
    }

    private void markMessageAsSent(String messageId) {
        MessageData data = repos.findById(messageId).orElseThrow();
        data.setStatus(HttpStatus.OK);
        repos.save(data);
    }

}
