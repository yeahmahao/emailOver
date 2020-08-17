package com.example.emailover.impl;

import com.example.emailover.model.email;
import com.example.emailover.service.emailService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class emailImpl implements emailService {

    private static String messageContent;
    private static emailImpl SendEmail;
    private static email em = new email();
    Timer ti;
    int i = 0;
    boolean timStatus = false;
    @Override
    public boolean emailgo(email ems) throws ParseException {
        em = ems;
        if(!timStatus){
            timStatus = true;
            emailTimer();

            return true;
        }
        return false;
    }

    /**
     * 查询发送次数
     *
     * */
    @Override
    public int getI() {
        return i;
    }

    /**
     * 停止发送
     *
     * */
    @Override
    public boolean stop() {
        if(timStatus){
            timStatus = false;
            if(ti != null){
                ti.cancel();
                System.out.println("停止发送邮件");
            }

            i = 0;
            return true;
        }
        return false;
    }
    /**
     * 启动发送线程
     *
     * */
    private void emailTimer() throws ParseException {
        //启动发送线程
        ti = new Timer();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.SECOND,(em.getTime()/1000) * 2);
        Date startTime = beforeTime.getTime();
        System.out.println(sd.format(startTime));
        Date startTime1 = sd.parse(sd.format(startTime));
        ti.schedule(new TimerTask() {
            @Override
            public void run() {
                emialGo();
            }
        },startTime1,em.getTime());

    }

    /**
     * 线程工作
     *
     * */
    private int emialGo(){

        try {
            //i累计发送次数
            i = i + SendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int SendEmail() throws Exception {
        SendEmail.messageContent = em.getTalkFont();
        //3.配置信息
        Properties props = new Properties();
        //设置发送邮件的协议
        props.put("mail.transport.protocol","smtp");//必须
        //设置发送邮件主机的主机名
        props.put("mail.smtp.host","smtp.qq.com");//必须
        //设置是否显示debug信息 true 会在控制台显示相关信息
        props.put("mail.debug","true");

        //4.创建session(可以理解为socket)
        //  但需要很多配置信息 所以在他之前需要配置 详见上面的4
        Session session = Session.getInstance(props);
        //5.通过session得到transport对象(可以理解为输出流)
        Transport ts = session.getTransport();
        //6.连上邮件服务器(想要通过邮箱输出/发送邮件 必须得到认证) 开启SMTP服务
        ts.connect(em.getUserName(),em.getSmtpNum());
        //7.创建邮件
        //  这个过程比较麻烦 单独在下面写一个方法
        //  调用下面静态方法 通过session创建一个Message对象
        //  这个message可以理解为file或class或frame)
        Message message = SendEmail.createMessage(session);
        //8.发送邮件(需要message对象 和 message设置的所有收件人)
        ((Transport) ts).sendMessage(message,message.getAllRecipients());
        //9.关闭通道
        ts.close();
        //10.发送完成
        System.out.println(em.getUserName()+"发送给"+em.getToUserName());
        System.out.println("新写的邮件发送成功啦");
        return 1;
    }
    private static MimeMessage createMessage(Session session) throws Exception {
        // 创建邮件
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        //邮件发送人
        message.setFrom(new InternetAddress(em.getUserName()));
        //邮件接收人   recipient容器 收纳
        //TO是发送人  CC是抄送人  BCC是暗送人  NEWSGROUPS新闻组
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(em.getToUserName()));
        //设置发送时间
        message.setSentDate(new Date());
        //设置邮件标题
        message.setSubject(em.getTitle());
        //如果是纯文本 这个方法就可以啦
        //正文
        message.setText(messageContent);
        message.saveChanges();
        System.out.println("邮件生成完毕！");
        // 返回创建好的的邮件
        return message;
    }

}
