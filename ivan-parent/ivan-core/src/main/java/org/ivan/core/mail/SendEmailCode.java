package org.ivan.core.mail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;
/**
 * Mail：发送邮件控制器
 * @author 周立波
 *
 */
public class SendEmailCode {
	// 邮件发送协议  
    private final static String PROTOCOL = "smtp";  
   
    // SMTP邮件服务器  
    private final static String HOST = "smtp.qq.com";  
   
    // SMTP邮件服务器默认端口  
    private final static String PORT = "465";  
   
    // 是否要求身份认证  
    private final static String IS_AUTH = "true";  
   
    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）  
    private final static String IS_ENABLED_DEBUG_MOD = "true";  
   
    // 发件人  
    private static String from = "475379135@qq.com";  
   
    // 收件人  
    private static String to = "zhoulibo_vip@163.com";  
   
    // 初始化连接邮件服务器的会话信息  
    private static Properties props = null;  
    static {
		try {
			props = new Properties();  
			// 指定协议  
	        props.put("mail.transport.protocol",PROTOCOL);  
	        // 主机 smtp.qq.com  
	        props.put("mail.smtp.host", HOST);  
	        // 端口  
	        props.put("mail.smtp.port", PORT);  
	        // 用户密码认证  
	        props.put("mail.smtp.auth",IS_AUTH);  
	        // 调试模式  
	        props.put("mail.debug", IS_ENABLED_DEBUG_MOD);    
	        MailSSLSocketFactory sf= new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
	        props.put("mail.smtp.ssl.enable", "true");
	        props.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
    }  
   
   
    /**
     * 发送简单的文本邮件
     */  
    public static boolean sendTextEmail(String to,int code) throws Exception {  
        try {
            // 创建Session实例对象  
            Session session1 = Session.getInstance(props);
            // 创建MimeMessage实例对象  
            MimeMessage message = new MimeMessage(session1);  
            // 设置发件人  
            message.setFrom(new InternetAddress(from));  
            // 设置邮件主题  
            message.setSubject("阿尔法GO");
            // 设置收件人  
            message.setRecipient(RecipientType.TO, new InternetAddress(to));  
            // 设置发送时间  
            message.setSentDate(new Date());  
            // 设置纯文本内容为邮件正文  
            message.setText("您的验证码是："+code+"!验证码有效期是1分钟，过期后请重新获取！"
                    + "【阿尔法GO科技】");  
            // 保存并生成最终的邮件内容  
            message.saveChanges();  
            Transport transport = session1.getTransport();
            transport.connect("smtp.qq.com", "475379135@qq.com", "qpawpnvgvmxxbjfa");
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }  
     
   
    public static boolean sendTextEmail() throws MessagingException, GeneralSecurityException {
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("seenews 错误");
        StringBuilder builder = new StringBuilder();
        builder.append("url = " + "http://blog.csdn.net/never_cxb/article/details/50524571");
        builder.append("\n页面爬虫错误");
        builder.append("\n时间 " + new Date().getTime());
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress("475379135@qq.com"));

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "475379135@qq.com", "qpawpnvgvmxxbjfa");

        transport.sendMessage(msg, new Address[] { new InternetAddress("zhoulibo_vip@163.com") });
        transport.close();
		return true;
    }
    /**
     * 发送简单的html邮件
     */  
    public static boolean sendHtmlEmail(String to,int code) throws Exception {  
        // 创建Session实例对象  
        Session session1 = Session.getInstance(props, new MyAuthenticator());  
   
        // 创建MimeMessage实例对象  
        MimeMessage message = new MimeMessage(session1);  
        // 设置邮件主题  
        message.setSubject("内燃机注册");  
        // 设置发送人  
        message.setFrom(new InternetAddress(from));  
        // 设置发送时间  
        message.setSentDate(new Date());  
        // 设置收件人  
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));  
        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk  
        message.setContent("<div style='width: 600px;margin: 0 auto'><h3 style='color:#003E64; text-align:center; '>内燃机注册验证码</h3><p style=''>尊敬的用户您好：</p><p style='text-indent: 2em'>您在注册内燃机账号，此次的验证码是："+code+",有效期10分钟!如果过期请重新获取。</p><p style='text-align: right; color:#003E64; font-size: 20px;'>中国内燃机学会</p></div>","text/html;charset=utf-8");  
         
      //设置自定义发件人昵称 
        String nick=""; 
        try { 
            nick=javax.mail.internet.MimeUtility.encodeText("中国内燃机学会"); 
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        }
        message.setFrom(new InternetAddress(nick+" <"+from+">"));
        // 保存并生成最终的邮件内容  
        message.saveChanges();  
   
        // 发送邮件  
        try {
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
      
    }  
   
    /**
     * 发送带内嵌图片的HTML邮件
     */  
    public static void sendHtmlWithInnerImageEmail() throws MessagingException {  
        // 创建Session实例对象  
        Session session = Session.getDefaultInstance(props, new MyAuthenticator());  
   
        // 创建邮件内容  
        MimeMessage message = new MimeMessage(session);  
        // 邮件主题,并指定编码格式  
        message.setSubject("带内嵌图片的HTML邮件", "utf-8");      
        // 发件人  
        message.setFrom(new InternetAddress(from));  
        // 收件人  
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));  
        // 抄送  
        message.setRecipient(RecipientType.CC, new InternetAddress("java_test@sohu.com"));  
        // 密送 (不会在邮件收件人名单中显示出来)  
        message.setRecipient(RecipientType.BCC, new InternetAddress("417067629@qq.com"));  
        // 发送时间  
        message.setSentDate(new Date());  
   
        // 创建一个MIME子类型为“related”的MimeMultipart对象  
        MimeMultipart mp = new MimeMultipart("related");  
        // 创建一个表示正文的MimeBodyPart对象，并将它加入到前面创建的MimeMultipart对象中  
        MimeBodyPart htmlPart = new MimeBodyPart();  
        mp.addBodyPart(htmlPart);  
        // 创建一个表示图片资源的MimeBodyPart对象，将将它加入到前面创建的MimeMultipart对象中  
        MimeBodyPart imagePart = new MimeBodyPart();  
        mp.addBodyPart(imagePart);  
   
        // 将MimeMultipart对象设置为整个邮件的内容  
        message.setContent(mp);  
   
        // 设置内嵌图片邮件体  
        DataSource ds = new FileDataSource(new File("resource/firefoxlogo.png"));  
        DataHandler dh = new DataHandler(ds);  
        imagePart.setDataHandler(dh);  
        imagePart.setContentID("firefoxlogo.png");  // 设置内容编号,用于其它邮件体引用  
   
        // 创建一个MIME子类型为"alternative"的MimeMultipart对象，并作为前面创建的htmlPart对象的邮件内容  
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");  
        // 创建一个表示html正文的MimeBodyPart对象  
        MimeBodyPart htmlBodypart = new MimeBodyPart();  
        // 其中cid=androidlogo.gif是引用邮件内部的图片，即imagePart.setContentID("androidlogo.gif");方法所保存的图片  
        htmlBodypart.setContent("<span style='color:red;'>这是带内嵌图片的HTML邮件哦！！！<img src=\"cid:firefoxlogo.png\" /></span>","text/html;charset=utf-8");  
        htmlMultipart.addBodyPart(htmlBodypart);  
        htmlPart.setContent(htmlMultipart);  
   
        // 保存并生成最终的邮件内容  
        message.saveChanges();  
   
        // 发送邮件  
        Transport.send(message);  
    }  
   
    /**
     * 发送带内嵌图片、附件、多收件人(显示邮箱姓名)、邮件优先级、阅读回执的完整的HTML邮件
     */  
    public static void sendMultipleEmail() throws Exception {  
        String charset = "utf-8";   // 指定中文编码格式  
        // 创建Session实例对象  
        Session session = Session.getInstance(props,new MyAuthenticator());  
   
        // 创建MimeMessage实例对象  
        MimeMessage message = new MimeMessage(session);  
        // 设置主题  
        message.setSubject("使用JavaMail发送混合组合类型的邮件测试");  
        // 设置发送人  
        message.setFrom(new InternetAddress(from,"新浪测试邮箱",charset));  
        // 设置收件人  
        message.setRecipients(RecipientType.TO,   
                new Address[] {  
                // 参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码  
                new InternetAddress("java_test@sohu.com", "张三_sohu", charset),  
                new InternetAddress("xyang0917@163.com", "李四_163", charset),  
            }  
        );  
        // 设置抄送  
        message.setRecipient(RecipientType.CC, new InternetAddress("xyang0917@gmail.com","王五_gmail",charset));  
        // 设置密送  
        message.setRecipient(RecipientType.BCC, new InternetAddress("xyang0917@qq.com", "赵六_QQ", charset));  
        // 设置发送时间  
        message.setSentDate(new Date());  
        // 设置回复人(收件人回复此邮件时,默认收件人)  
        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("田七") + "\" <417067629@qq.com>"));  
        // 设置优先级(1:紧急   3:普通    5:低)  
        message.setHeader("X-Priority", "1");  
        // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)  
        message.setHeader("Disposition-Notification-To", from);  
   
        // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件  
        MimeMultipart mailContent = new MimeMultipart("mixed");   
        message.setContent(mailContent);  
   
        // 附件  
        MimeBodyPart attach1 = new MimeBodyPart();  
        MimeBodyPart attach2 = new MimeBodyPart();  
        // 内容  
        MimeBodyPart mailBody = new MimeBodyPart();  
   
        // 将附件和内容添加到邮件当中  
        mailContent.addBodyPart(attach1);  
        mailContent.addBodyPart(attach2);  
        mailContent.addBodyPart(mailBody);  
   
        // 附件1(利用jaf框架读取数据源生成邮件体)  
        DataSource ds1 = new FileDataSource("resource/Earth.bmp");  
        DataHandler dh1 = new DataHandler(ds1);  
        attach1.setFileName(MimeUtility.encodeText("Earth.bmp"));  
        attach1.setDataHandler(dh1);  
   
        // 附件2  
        DataSource ds2 = new FileDataSource("resource/如何学好C语言.txt");  
        DataHandler dh2 = new DataHandler(ds2);  
        attach2.setDataHandler(dh2);  
        attach2.setFileName(MimeUtility.encodeText("如何学好C语言.txt"));  
   
        // 邮件正文(内嵌图片+html文本)  
        MimeMultipart body = new MimeMultipart("related");  //邮件正文也是一个组合体,需要指明组合关系  
        mailBody.setContent(body);  
   
        // 邮件正文由html和图片构成  
        MimeBodyPart imgPart = new MimeBodyPart();  
        MimeBodyPart htmlPart = new MimeBodyPart();  
        body.addBodyPart(imgPart);  
        body.addBodyPart(htmlPart);  
   
        // 正文图片  
        DataSource ds3 = new FileDataSource("resource/firefoxlogo.png");  
        DataHandler dh3 = new DataHandler(ds3);  
        imgPart.setDataHandler(dh3);  
        imgPart.setContentID("firefoxlogo.png");  
   
        // html邮件内容  
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");   
        htmlPart.setContent(htmlMultipart);  
        MimeBodyPart htmlContent = new MimeBodyPart();  
        htmlContent.setContent(  
                "<span style='color:red'>这是我自己用java mail发送的邮件哦！" +  
                "<img src='cid:firefoxlogo.png' /></span>"  
                        , "text/html;charset=gbk");  
        htmlMultipart.addBodyPart(htmlContent);  
   
        // 保存邮件内容修改  
        message.saveChanges();  
   
        /*File eml = buildEmlFile(message);
        sendMailForEml(eml);*/  
   
        // 发送邮件  
        Transport.send(message);  
    }  
   
    /**
     * 将邮件内容生成eml文件
     * @param message 邮件内容
     */  
    public static File buildEmlFile(Message message) throws MessagingException, FileNotFoundException, IOException {  
        File file = new File("c:\\" + MimeUtility.decodeText(message.getSubject())+".eml");  
        message.writeTo(new FileOutputStream(file));  
        return file;  
    }  
   
    /**
     * 发送本地已经生成好的email文件
     */  
    public static void sendMailForEml(File eml) throws Exception {  
        // 获得邮件会话  
        Session session = Session.getInstance(props,new MyAuthenticator());  
        // 获得邮件内容,即发生前生成的eml文件  
        InputStream is = new FileInputStream(eml);  
        MimeMessage message = new MimeMessage(session,is);  
        //发送邮件  
        Transport.send(message);  
    }
    public static void main(String[] args) throws Exception {
//      sendHtmlEmail("zhoulibo_vip@163.com", 88888);
      sendTextEmail("zhoulibo_vip@163.com", 88888);
//      sendTextEmail();
  }
}
