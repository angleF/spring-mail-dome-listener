package com.ezhiyang.mail.basic.listener;

import com.ezhiyang.mail.context.MailResultQueueContext;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.basic.listener
 * @Description:
 * @date 2018/8/21/0021 14:06:52
 */
public class MyTransportListener implements TransportListener {

    @Override
    public void messageDelivered(TransportEvent e) {
        System.err.println("发送成功！！！！！！！！！！");
        System.err.println("发送地址：");
        try {
            String subject = e.getMessage().getSubject();
            Address[] sentAddresses = e.getValidSentAddresses();
            for (Address sentAddress : sentAddresses) {
                InternetAddress address = (InternetAddress) sentAddress;
                System.err.println("地址："+address.getAddress());
                MailResultQueueContext.addDeliveredAddress(subject,address.getAddress());
            }
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
        System.err.println("发送失败！！！！！！！！！！");
        try {
            String subject = e.getMessage().getSubject();
            Address[] sentAddresses = e.getInvalidAddresses();
            for (Address sentAddress : sentAddresses) {
                InternetAddress address = (InternetAddress) sentAddress;
                System.err.println("地址："+address.getAddress());
                MailResultQueueContext.addNotDeliveredAddress(subject,address.getAddress());
            }
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        System.err.println("发送部分失败！！！！！！！！！！");
    }
}
