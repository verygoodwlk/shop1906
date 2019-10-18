package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class RabbitMailListener {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = "mail_queue")
    public void sendMail(Email email){
        System.out.println("需要发送邮件：" + email);
        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //创建一个邮件的包装工具
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            //设置邮件的标题
            mimeMessageHelper.setSubject(email.getSubject());
            //设置发送方
            mimeMessageHelper.setFrom(from);
            //设置接收方法
            //TO - 普通收件人
            //CC - 抄送人
            //BCC - 密送人
            mimeMessageHelper.setTo(email.getTo());
            //设置邮件内容
            mimeMessageHelper.setText(email.getContent(), true);
            //设置附件
//            mimeMessageHelper.addAttachment("为了联盟.jpg",
//                    new File("C:\\Users\\Ken\\Pictures\\Saved Pictures\\联盟.jpg"));
            //设置发送时间
            mimeMessageHelper.setSentDate(new Date());
            //发送邮件
            javaMailSender.send(mimeMessage);
            System.out.println("邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
