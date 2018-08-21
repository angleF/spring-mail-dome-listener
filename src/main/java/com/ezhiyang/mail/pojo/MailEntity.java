package com.ezhiyang.mail.pojo;

import lombok.*;

/**
 * @author zhaoliang.fu
 * @version V1.0
 * @Package com.ezhiyang.mail.pojo
 * @Description:
 * @date 2018/8/21/0021 10:46:55
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailEntity {
    /**
     * 发件人
     */
    private String from;
    /**
     * 收件人
     */
    private String[] recipients;
    /**
     * 抄送人
     */
    private String[] carbonCopy;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String text;

    private Boolean isHtml;
}
