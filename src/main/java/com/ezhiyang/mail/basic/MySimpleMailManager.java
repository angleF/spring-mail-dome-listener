package com.ezhiyang.mail.basic;

import com.ezhiyang.mail.IMailManager;
import com.ezhiyang.mail.pojo.MailEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.basic
 * @Description:
 * @date 2018/8/21/0021 10:45:29
 */
@Getter
@Setter
public class MySimpleMailManager implements IMailManager<MailEntity,Boolean> {

    private MailSender mailSender;

    private SimpleMailMessage templateMessage;

    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public Boolean syncSend(MailEntity mailEntity) {
        try {
            SimpleMailMessage msg=new SimpleMailMessage(templateMessage);
            msg.setTo(mailEntity.getRecipients());
            msg.setText(mailEntity.getText());
            msg.setSubject(mailEntity.getSubject());
            msg.setCc(mailEntity.getCarbonCopy());
            mailSender.send(msg);
        }catch (Exception e){
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
