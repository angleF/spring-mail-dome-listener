package com.ezhiyang.mail.mime;

import com.ezhiyang.mail.IMailManager;
import com.ezhiyang.mail.pojo.MailEntity;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.mime
 * @Description:
 * @date 2018/8/21/0021 11:37:25
 */
@Component
@Setter
public class MimeMailManager  implements IMailManager<MailEntity,Boolean> {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Override
    public Boolean syncSend(MailEntity entity) {
        MimeMessage message=javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom(entity.getFrom());
            mimeMessageHelper.setTo(entity.getRecipients());
            mimeMessageHelper.setCc(entity.getCarbonCopy());
            mimeMessageHelper.setSubject(entity.getSubject());
            mimeMessageHelper.setText(entity.getText(),entity.getIsHtml());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean asyncSend(final MailEntity mailEntity) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                syncSend(mailEntity);
            }
        });
        return Boolean.TRUE;
    }
}
