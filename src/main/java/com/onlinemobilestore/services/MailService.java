package com.onlinemobilestore.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
        mailSender.send(message);
    }
    public void sendWelcomeMail(String to) {
        String subject = "Welcome to Our Online Mobile Store";
        String content = "Dear User,\n\nWelcome to Our Online Mobile Store. Thank you for registering with us!\n\nBest regards,\nYour Online Mobile Store Team";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
        mailSender.send(message);
    }

    public void sendPaymentConfirmation(String email, String paymentCode, double amount) {
        String subject = "Confirmation of Payment";
        String content = "Your payment with the code: " + paymentCode + " has been received successfully. The total amount paid is: " + amount + " VND. Thank you for your purchase.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

        // Xóa đơn hàng sau khi thanh toán thành công
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
//        if (optionalOrder.isPresent()) {
//            Order order = optionalOrder.get();
//            orderRepository.delete(order);
//        }
    }
}
