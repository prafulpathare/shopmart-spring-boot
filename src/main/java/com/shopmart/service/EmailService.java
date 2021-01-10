package com.shopmart.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.shopmart.util.Mail;

@Service
public class EmailService {
	
	@Value("${spring.mail.username}") private String username;
    @Autowired private JavaMailSender emailSender;
    @Autowired private SpringTemplateEngine templateEngine;
	private static Logger log = LoggerFactory.getLogger(EmailService.class);


    public void sendEmail(Mail mail) {
    	try {
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	        // helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
	
	        Context context = new Context();
	        context.setVariables(mail.getProps());
	        String html = templateEngine.process(mail.getTemplateName(), context);
	        helper.setText(html, true);
	        helper.setSubject(mail.getSubject());
	        helper.setFrom(username);
	        helper.setTo(mail.getMailTo());
	        emailSender.send(message);
	        log.info("[email] "+mail.getMailTo()+" | subject: "+mail.getSubject());
    	}
    	catch (Exception e) {
			e.printStackTrace();
    	}
    }

}