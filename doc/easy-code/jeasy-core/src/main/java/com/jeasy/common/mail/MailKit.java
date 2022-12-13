package com.jeasy.common.mail;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.template.VelocityKit;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 邮件发送的工具类 <br>
 * 使用时，必须先引入 ：<import resource="classpath*:/applicationContext-mail.xml" /><br>
 * MailUtils.send("ni350305@sina.com", "ni350305@163.com", "标题",
 * "mailTemplateTest.vm", map);
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class MailKit {

    /** */
    private static MailService mailService;

    /**
     * 发送邮件
     *
     * @param to
     * @param subject
     * @throws Exception
     */
    public static void send(String to, String subject) throws Exception {
        getMailService().send(to, subject);
    }

    /**
     * 发送邮件
     *
     * @param to           the to
     * @param subject      the subject
     * @param templateName the template name
     * @param map          the map
     * @throws Exception
     */
    public static void send(String to, String subject, String templateName, Map<String, Object> map) throws Exception {
        getMailService().send(to, subject, templateName, map);
    }

    /**
     * Send.
     *
     * @param to           the to
     * @param subject      the subject
     * @param templateName the template name
     * @param map          the map
     * @throws Exception
     */
    public static void send(String[] to, String subject, String templateName, Map<String, Object> map) throws Exception {
        getMailService().send(to, subject, templateName, map);
    }

    /**
     * Send.
     *
     * @param to           the to
     * @param subject      the subject
     * @param templateName the template name
     * @param map          the map
     * @param fileName     the file name
     * @throws Exception
     */
    public static void send(String to, String subject, String templateName, Map<String, Object> map, String fileName) throws Exception {
        getMailService().send(to, subject, templateName, map, fileName);
    }

    /**
     * send text to user
     *
     * @param to      the to
     * @param subject the subject
     * @param context the context
     * @throws Exception
     */
    public static void sendText(String to, String subject, String context) throws Exception {
        getMailService().sendText(new String[]{to}, subject, context);
    }

    /**
     * 文本发送器，多邮箱.
     *
     * @param to      the to
     * @param subject the subject
     * @param context the context
     * @throws Exception
     */
    public static void sendText(String[] to, String subject, String context) throws Exception {
        getMailService().sendText(to, subject, context);
    }

    /**
     * @return
     * @throws Exception
     * @auth nibili 2015年4月16日
     */
    private static MailService getMailService() throws Exception {
        if (mailService == null) {
            mailService = SpringContextHolder.getBean(MailService.class);
        }
        if (mailService == null) {
            throw new RuntimeException(StrKit.S_EMPTY);
        }
        return mailService;
    }

    /**
     * 邮件服务
     */
    public static class MailService {

        /**
         * The mail sender.
         */
        private JavaMailSender mailSender;

        /**
         * The velocity engine.
         */
        private VelocityEngine velocityEngine;

        /**
         * 发送端
         **/
        private String mailFrom;

        /**
         * send text to user
         *
         * @param to      the to
         * @param subject the subject
         * @param context the context
         */
        public void sendText(String to, String subject, String context) {
            sendText(new String[]{to}, subject, context);
        }

        /**
         * 文本发送器，多邮箱.
         *
         * @param to      the to
         * @param subject the subject
         * @param context the context
         */
        public void sendText(String[] to, String subject, String context) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(this.getMailFrom());
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(context);
            simpleMailMessage.setSentDate(new Date());
            try {
                mailSender.send(simpleMailMessage);
                log.info("纯文本邮件已发送至{}", StrKit.join(StrKit.S_COMMA, (Object[]) simpleMailMessage.getTo()));
            } catch (Exception e) {
                log.error("发送邮件失败", e);
            }

        }

        /**
         * Send.
         *
         * @param to      the to
         * @param subject the subject
         * @throws Exception
         */
        public void send(String to, String subject) throws Exception {
            send(to, subject, null, null);
        }

        /**
         * Send.
         *
         * @param to           the to
         * @param subject      the subject
         * @param templateName the template name
         * @param map          the map
         * @throws Exception
         */
        public void send(String to, String subject, String templateName, Map<String, Object> map) throws Exception {
            send(new String[]{to}, subject, templateName, map, null);
        }

        /**
         * Send.
         *
         * @param to           the to
         * @param subject      the subject
         * @param templateName the template name
         * @param map          the map
         * @throws Exception
         */
        public void send(String[] to, String subject, String templateName, Map<String, Object> map) throws Exception {
            send(to, subject, templateName, map, null);
        }

        /**
         * Send.
         *
         * @param to           the to
         * @param subject      the subject
         * @param templateName the template name
         * @param map          the map
         * @param fileName     the file name
         * @throws Exception
         */
        public void send(String to, String subject, String templateName, Map<String, Object> map, String fileName) throws Exception {
            send(new String[]{to}, subject, templateName, map, fileName);
        }

        /**
         * Send.
         *
         * @param to           the to
         * @param subject      the subject
         * @param templateName the template name
         * @param map          the map
         * @param fileName     the file name
         * @throws Exception
         */
        public void send(String[] to, String subject, String templateName, Map<String, Object> map, String fileName) throws Exception {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, CharsetKit.UTF_8);
                messageHelper.setFrom(this.getMailFrom());
                messageHelper.setTo(to);
                messageHelper.setSubject(subject);
                if (templateName == null || StrKit.S_EMPTY.equals(templateName)) {
                    templateName = "base.ftl";
                }
                if (map == null || map.isEmpty()) {
                    map = Maps.newHashMap();
                }
                String context = VelocityKit.renderFile(templateName, velocityEngine, CharsetKit.UTF_8, map);
                log.debug("邮件内容:" + context);
                messageHelper.setText(context, true);
                if (!(fileName == null || StrKit.S_EMPTY.equals(fileName.trim()))) {
                    Map<String, Object> fileMap = generateAttachment(fileName);
                    messageHelper.addAttachment((String) fileMap.get("fileName"), (File) fileMap.get("file"));
                }
                mailSender.send(message);
                log.info("HTML版邮件已发送至{}", StrKit.join(StrKit.S_COMMA, (Object[]) to));
            } catch (MessagingException e) {
                throw new Exception("构造邮件失败", e);
            } catch (Exception e) {
                throw new Exception("发送邮件失败", e);
            }

        }

        /**
         * Generate attachment.
         *
         * @param fileName the file name
         * @return the map
         * @throws MessagingException the messaging exception
         */
        private Map<String, Object> generateAttachment(String fileName) throws MessagingException {
            Map<String, Object> map = Maps.newHashMap();
            try {
                Resource resource = new UrlResource(fileName);
                map.put("file", resource.getFile());
                map.put("fileName", resource.getFilename());
                return map;
            } catch (IOException e) {
                log.error("构造邮件失败,附件文件不存在", e);
                throw new MessagingException("附件文件不存在", e);
            }
        }

        /**
         * Spring的MailSender.
         *
         * @param mailSender the new mail sender
         */
        public void setMailSender(JavaMailSender mailSender) {
            this.mailSender = mailSender;
        }

        /**
         * Sets the velocity engine.
         *
         * @param velocityEngine the new velocity engine
         */
        public void setVelocityEngine(VelocityEngine velocityEngine) {
            this.velocityEngine = velocityEngine;
        }

        public void setMailFrom(String mailFrom) {
            this.mailFrom = mailFrom;
        }

        public String getMailFrom() {
            return this.mailFrom;
        }
    }
}
