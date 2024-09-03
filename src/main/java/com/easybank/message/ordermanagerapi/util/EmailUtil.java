package com.easybank.message.ordermanagerapi.util;

import com.easybank.message.ordermanagerapi.entity.Order;
import com.easybank.message.ordermanagerapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderCompletionEmail(User user, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Order #" + order.getId() + " Completed");
        message.setText("Hello " + user.getName() + ",\n\nYour order #" + order.getId() + " has been fulfilled.");

        mailSender.send(message);
        LogUtil.logEmailSent(user, order);
    }

}
