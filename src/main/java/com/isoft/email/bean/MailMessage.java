package com.isoft.email.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;

/**
 * 封装邮件相关的内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage implements Serializable {
    private String subject , text ;
    /**
     * 接收者
     */
    private String to ;
    /**
     * 附件名称
     */
    private String attachmentFileName ;
    /**
     * 附件
     */
    private File attachmentFile ;
}
