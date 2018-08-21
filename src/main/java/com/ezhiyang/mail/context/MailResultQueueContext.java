package com.ezhiyang.mail.context;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.context
 * @Description:
 * @date 2018/8/21/0021 14:15:31
 */
public class MailResultQueueContext {

    /**
     * 消息送达成功主题Map
     * key: 邮件主题
     * value:发送成功邮箱地址
     */
    public static final ConcurrentHashMap<String,List<String>> messageDeliveredSubject=new ConcurrentHashMap<>();

    /**
     * 消息未送达主题Map
     * key: 邮件主题
     * value:未送达邮箱地址
     */
    public static final ConcurrentHashMap<String,List<String>> messageNotDeliveredSubject=new ConcurrentHashMap<>();

    /**
     * 清空
     */
    public static void clear(){
        messageDeliveredSubject.clear();
        messageNotDeliveredSubject.clear();
    }

    public static List<String> getDeliveredSubject(String subject){
        return messageDeliveredSubject.get(subject);
    }

    public static List<String> getNotDeliveredSubject(String subject){
        return messageNotDeliveredSubject.get(subject);
    }

    /**
     * 添加成功发送主题及地址
     * @param subject 主题
     * @param address 邮件接收地址数组
     * @return
     */
    public static List<String> addDeliveredAddress(String subject,String... address){
        return MailResultQueueContext.add(messageDeliveredSubject,subject,address);
    }

    private static List<String> checkAddress(String subject,String... address) {
        Assert.hasText(subject,"subject is not null");
        Assert.notEmpty(address,"address is not null and length more than the 1");
        List<String> list=new ArrayList<>();
        Collections.addAll(list,address);
        return list;
    }

    private static List<String> add(ConcurrentHashMap<String,List<String>> concurrentHashMap,String subject,String... address){
        if (concurrentHashMap.containsKey(subject)) {
            List<String> list = concurrentHashMap.get(subject);
            List<String> newList = checkAddress(subject, address);
            if (CollectionUtils.isEmpty(list)) {
                list=newList;
            }else{
                list.addAll(newList);
            }
            return concurrentHashMap.put(subject,list);
        }else{
            return concurrentHashMap.put(subject,checkAddress(subject,address));
        }
    }

    /**
     * 添加未发送主题及地址
     * @param subject 主题
     * @param address 邮件接收地址数组
     * @return
     */
    public static List<String> addNotDeliveredAddress(String subject,String... address){
        return  MailResultQueueContext.add(messageNotDeliveredSubject,subject,address);
    }
}
