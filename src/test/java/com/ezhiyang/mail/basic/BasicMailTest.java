package com.ezhiyang.mail.basic;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.basic
 * @Description:
 * @date 2018/8/21/0021 11:07:11
 */

import com.ezhiyang.mail.IMailManager;
import com.ezhiyang.mail.context.MailResultQueueContext;
import com.ezhiyang.mail.pojo.MailEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-basic.xml")
public class BasicMailTest {

    @Resource
    private IMailManager iMailManager;

    private  MailEntity mailEntity;

    @Before
    public void beforeTest() {
        mailEntity = MailEntity.builder().subject("Simple Basic Test").text("这是一封测试邮件！")
                .carbonCopy(new String[]{"fzl_code@163.com"}).recipients(new String[]{"zhaoliang.fu@ezhiyang.com"}).build();
    }

    @Test
    public void syncTest() throws InterruptedException {
        mailEntity.setText("这是一封测试邮件！同步的<a href='www.baidu.com' >baidu</a>");
        iMailManager.syncSend(mailEntity);
        ConcurrentHashMap<String, List<String>> deliveredSubject = MailResultQueueContext.messageDeliveredSubject;
        for (Map.Entry<String, List<String>> entry : deliveredSubject.entrySet()) {
            System.out.println("Subject:"+entry.getKey());
            System.out.println("Address:"+ StringUtils.collectionToCommaDelimitedString(entry.getValue()));
        }
    }

    @Test
    public void asyncTest() throws InterruptedException {
        mailEntity.setText("这是一封测试邮件！异步的<a href='www.baidu.com' >baidu</a>");
        iMailManager.asyncSend(mailEntity);
        Thread.sleep(50000);
        ConcurrentHashMap<String, List<String>> deliveredSubject = MailResultQueueContext.messageDeliveredSubject;
        for (Map.Entry<String, List<String>> entry : deliveredSubject.entrySet()) {
            System.out.println("Subject:"+entry.getKey());
            System.out.println("Address:"+ StringUtils.collectionToCommaDelimitedString(entry.getValue()));
        }
    }

    public static void main(String[] args) {
        MailResultQueueContext.addDeliveredAddress("Simple Basic Test",new String[]{"fzl_code@163.com"});
        MailResultQueueContext.addDeliveredAddress("Simple Basic Test",new String[]{"zhaoliang.fu@ezhiyang.com"});
        ConcurrentHashMap<String, List<String>> deliveredSubject = MailResultQueueContext.messageDeliveredSubject;
        for (Map.Entry<String, List<String>> entry : deliveredSubject.entrySet()) {
            System.out.println("Subject:"+entry.getKey());
            System.out.println("Address:"+ StringUtils.collectionToCommaDelimitedString(entry.getValue()));
        }
    }
}
