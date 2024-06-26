/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.serviceDelivery;

import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author PHONG PC
 */
public class MailSender {
     public String sendMail(String tinNhan , List<String> mailTo){
         // Thông tin email của người gửi
        String senderEmail = "phongvvutuan@gmail.com";
        String senderPassword = "pnok mxsy totc labb";
//        String senderPassword = "pnok mxsy totc labb";

        // Danh sách các địa chỉ email nhận
        // Cấu hình Properties cho việc gửi email thông qua SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server của bạn
        properties.put("mail.smtp.port", "587"); // Port SMTP (thường là 587)

        // Tạo đối tượng Authenticator để xác thực người gửi
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };

        // Tạo phiên làm việc của JavaMail
        Session session = Session.getInstance(properties, authenticator);

        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));

            // Thêm các địa chỉ email nhận vào đối tượng MimeMessage
            for (String recipientEmail : mailTo) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            }
            
//            MimeBodyPart contentPart = new MimeBodyPart();
//            contentPart.setContent(tinNhan, "text/html; charset=urf-8");
            // Đặt chủ đề và nội dung của email
            message.setSubject("T-SHIRT BEE SHOP");
            message.setContent(tinNhan, "text/html; charset=utf-8");
            // Gửi email
            Transport.send(message);

            System.out.println("Email sent successfully.");
            return "ko lỗi gì";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "lỗi gì đấy";
        }
     }
}
