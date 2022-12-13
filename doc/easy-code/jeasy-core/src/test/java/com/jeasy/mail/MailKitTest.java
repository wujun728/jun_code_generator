package com.jeasy.mail;

import com.jeasy.common.mail.MailKit;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/mail/spring-mail.xml")
public class MailKitTest {

    @Test
    public void send() {
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("to", "ni350305@163.com");
            map.put("from", "ni350305@163.com");
            map.put("subject", "标题");
            MailKit.send("ni350305@163.com", "标题", "mailTemplate.vm", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
