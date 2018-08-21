package com.ezhiyang.mail;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail
 * @Description:
 * @date 2018/8/21/0021 10:43:03
 */
public interface IMailManager<T,R> {

    R syncSend(T t);

    R asyncSend(T t);
}
