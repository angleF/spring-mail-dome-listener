package com.ezhiyang.mail.mime;

import com.ezhiyang.mail.IMailManager;
import com.ezhiyang.mail.pojo.MailEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.mime
 * @Description:
 * @date 2018/8/21/0021 12:59:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-mime.xml")
public class MimeMailTest {

    @Resource
    private IMailManager mimeMailManager;

    private MailEntity mailEntity;
    @Before
    public void beforeTest() {
        String fromStr="fzl_code@163.com";
        mailEntity = MailEntity.builder().subject("Mime Basic Test").text("这是一封测试邮件！<a href='www.baidu.com' >baidu</a>").from(fromStr)
                .carbonCopy(new String[]{fromStr}).recipients(new String[]{"zhaoliang.fu@ezhiyang.com"}).isHtml(Boolean.TRUE).build();
    }

    @Test
    public void syncTest() throws InterruptedException {
        mailEntity.setText("这是一封测试邮件！同步的<a href='www.baidu.com' >baidu</a>");
        mimeMailManager.asyncSend(mailEntity);
    }

    @Test
    public void asyncTest() throws InterruptedException {
        mailEntity.setText("这是一封测试邮件！异步的<a href='www.baidu.com' >baidu</a>");
        mimeMailManager.asyncSend(mailEntity);
        Thread.sleep(50000);
    }
}
