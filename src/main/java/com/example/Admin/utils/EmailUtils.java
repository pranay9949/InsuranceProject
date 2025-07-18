package com.example.Admin.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    @Autowired
    JavaMailSender javaMailSender;

    public  Boolean sendMail(String to , String subject , String body){
        Boolean isMailsent = false;
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);
            javaMailSender.send(mimeMessage);
            isMailsent = true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return  isMailsent;
    }
}
