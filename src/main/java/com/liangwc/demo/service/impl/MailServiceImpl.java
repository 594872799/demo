package com.liangwc.demo.service.impl;

import com.liangwc.demo.base.lang.Result;
import com.liangwc.demo.service.MailService;
import com.sun.mail.util.MailSSLSocketFactory;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author liangweicheng
 * @date 2019/11/22 14:36
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public Result send(String template, String email) {
        String code = RandomStringUtils.randomNumeric(6);
        redisTemplate.opsForValue().set(email, code, 60L, TimeUnit.SECONDS);
        Map<String, Object> context = new HashMap<>(1);
        context.put("code", code);
        try {
            // 创建Session实例对象
            Session session = createSession();
            // 创建MimeMessage实例对象
            MimeMessage message = createMessage(session, template, context);
            // 发送邮件
            Transport.send(message);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    private Session createSession() throws GeneralSecurityException {
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "false");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("594872799@qq.com", "ywmkrmyilzojbcfj");
            }
        });
    }

    private MimeMessage createMessage(Session session, String template, Map<String, Object> code) throws Exception {
        // 创建MimeMessage实例对象
        MimeMessage msg = new MimeMessage(session);
        // 设置标题
        msg.setSubject("m_blog验证码");
        // 发送的邮箱地址
        msg.setFrom(new InternetAddress("594872799@qq.com"));
        msg.setRecipients(Message.RecipientType.TO, "594872799@qq.com");
        // 设置发生日期
        msg.setSentDate(new Date());
        /* 创建用于组合邮件正文和附件的MimeMultipart对象 */
        MimeMultipart multipart = new MimeMultipart();
        // 设置HTML内容
        MimeBodyPart content = createContent(render(template, code));
        multipart.addBodyPart(content);
        msg.setContent(multipart);
        msg.saveChanges();
        return msg;
    }

    private static MimeBodyPart createContent(String body) throws Exception {

        /* 创建代表组合MIME消息的MimeMultipart对象和该对象保存到的MimeBodyPart对象 */
        MimeBodyPart content = new MimeBodyPart();

        // 创建一个MImeMultipart对象
        MimeMultipart multipart = new MimeMultipart();

        // 创建一个表示HTML正文的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
        multipart.addBodyPart(htmlBodyPart);

        // 将MimeMultipart对象保存到MimeBodyPart对象中
        content.setContent(multipart);

        return content;
    }

    private String render(String templateName, Map<String, Object> model) {
        try {
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName, "UTF-8");
            t.setOutputEncoding("UTF-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
