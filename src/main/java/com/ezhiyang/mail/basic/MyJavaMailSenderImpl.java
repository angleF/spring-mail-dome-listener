package com.ezhiyang.mail.basic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.event.TransportListener;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.basic
 * @Description:
 * @date 2018/8/21/0021 14:02:00
 */
@Setter
@Getter
public class MyJavaMailSenderImpl  extends JavaMailSenderImpl {

    private TransportListener transportListener;

    @Override
    protected Transport connectTransport() throws MessagingException {
        Transport transport = super.connectTransport();
        transport.addTransportListener(transportListener);
        return transport;
    }
}
