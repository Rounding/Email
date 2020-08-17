package com.isoft.email.controller;

import com.isoft.email.bean.ActiveMessage;
import com.isoft.email.bean.MailMessage;
import com.isoft.email.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/email")
public class EmaiController {
    @Autowired
    MailUtil mailUtil ;

    @GetMapping("simple")
    public String sendSimple() {
        MailMessage mailMessage = new MailMessage() ;
        mailMessage.setTo("470323372@qq.com");
        mailMessage.setSubject("测试");
        mailMessage.setText("这是一个<b>测试</b>邮件");
        mailUtil.sendSimpleMail(mailMessage);
        return "ok" ;
    }

    @GetMapping("html")
    public String sendHtml() {
        MailMessage mailMessage = new MailMessage() ;
        mailMessage.setTo("470323372@qq.com");
        mailMessage.setSubject("测试HTML邮件");
        String text = "<html><head></head><body>" +
                "<a href='http://www.baidu.com'>百度</a>" +
                "<p>这是一个<B>段落</B></p>" +
                "</body></html>" ;
        mailMessage.setText(text);
        mailUtil.sendMailHtml(mailMessage);
        return "html -- ok" ;
    }

    @GetMapping("attachment")
    public String sendAttachment(HttpServletRequest request) {
        MailMessage mailMessage = new MailMessage() ;
        mailMessage.setTo("470323372@qq.com");
        mailMessage.setSubject("测试HTML邮件");
        String text = "<html><head></head><body>" +
                "<p>这是一个带有<B>附件</B>的邮件</p>" +
                "</body></html>" ;
        mailMessage.setText(text);
        String fname = "编码修改.rar" ;
        String path = request.getServletContext().getRealPath("/") + "/WEB-INF/classes/static/attachment/" + fname ;
        File file = new File(path) ;
        mailMessage.setAttachmentFileName(fname);
        mailMessage.setAttachmentFile(file);
        mailUtil.sendMailAttachment(mailMessage , true);
        return "attachment -- ok" ;
    }

    @Value("${com.email.ip}")
    String serverIP ;
    @Value(("${com.email.port}"))
    String serverPort ;

    @Autowired
    TemplateEngine templateEngine ;
    // 发送激活电子邮件
    @GetMapping("sendActive")
    public String sendActive() {
        String url = "email/active" ;
        String activeCode = UUID.randomUUID().toString().replace("-" , "") ;
        Integer id = 101 ;
        String title = "激活账号" ;
        String to = "470323372@qq.com" ;

        ActiveMessage activeMessage = new ActiveMessage() ;
        activeMessage.setIp(serverIP);
        activeMessage.setPort(serverPort);
        activeMessage.setUrl(url);
        activeMessage.setActiveCode(activeCode);
        activeMessage.setId(String.valueOf(id));
        Map<String , Object> map = new HashMap<>() ;
//        map.put("params" , 含有 params.xxx 的若干成员的对象) ;
        map.put("params" , activeMessage) ;
        // 获取模板页内容，填充模板页中的参数
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context() ;
        context.setVariables(map);
        String text = templateEngine.process("ActiveTemplate.html" , context) ;

        MailMessage mailMessage = new MailMessage() ;
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(text);
        mailUtil.sendMailHtml(mailMessage);
        return "send Active Email -- ok" ;
    }

    // 用户点击激活链接后的处理
    @GetMapping("active")
    public String active(String activeCode , Integer id) {
        System.out.println(activeCode + " ," + id);
        // update db
        return "active -- ok" ;
    }
}





















