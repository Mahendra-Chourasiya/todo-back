package com.mahendra.todoapp.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String username) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to Taskify, " + username + "!");

            // HTML content with inline image reference
            String htmlContent = "<html><body style='font-family: Arial, sans-serif; color: #333;'>"
                + "<div style='text-align: center; padding: 20px;'>"
                + "<img src='cid:logoImage' style='width: 200px; height: auto;' alt='Taskify Logo'>"  // 'cid:logoImage' is used as the content ID for the inline image
                + "</div>"
                + "<div style='text-align: center; padding: 20px;'>"
                + "<h1 style='color: #4A90E2;'>Welcome to Taskify, " + username + "!</h1>"
                + "<p style='font-size: 16px;'>Thank you for joining Taskify, the premier task management solution designed to supercharge your productivity. We're thrilled to have you with us and can't wait for you to experience how Taskify can transform your task management.</p>"
                + "<p style='font-size: 16px;'>To get started, click the button below to log in to your account:</p>"
                + "<a href='https://taskify-frontend.vercel' style='display: inline-block; background-color: #4A90E2; color: #ffffff; padding: 15px 25px; text-decoration: none; border-radius: 5px; font-size: 16px;'>Login to Taskify</a>"
                + "<p style='font-size: 16px; margin-top: 20px;'>If you have any questions or need assistance, our support team is here to help. Just reply to this email or reach out to us directly.</p>"
                + "<p style='font-size: 16px;'>Best regards,<br>"
                + "<strong>Team Taskify</strong><br>"
                + "Indore, Madhya Pradesh<br>"
                + "Phone: 9340083775<br>"
                + "Email: <a href='mailto:support@taskify.com' style='color: #4A90E2;'>support@taskify.com</a></p>"
                + "<div style='text-align: center; padding: 20px;'>"
                + "<p style='font-size: 14px; color: #888;'>© 2024 Taskify. All rights reserved.</p>"
                + "</div>"
                + "</body></html>";

            helper.setText(htmlContent, true);

            // Correct path to the logo image (adjust based on the actual running directory)
            FileSystemResource resource = new FileSystemResource(new File("frontend/src/assets/logo.png"));
            helper.addInline("logoImage", resource);  // 'logoImage' must match the 'cid' used in the HTML content

            mailSender.send(message);
            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
