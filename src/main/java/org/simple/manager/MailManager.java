package org.simple.manager;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.simple.dto.MailSendRecordDTO;
import org.simple.exception.ServiceException;

/**
 * 邮件
 */
public class MailManager {
	private static final Logger logger = Logger.getLogger(MailManager.class);
    private static final String ACCOUNT = "lwzhang1992@126.com";
    private static final String PASSWORD = "ZLW11214610";
    private static final String SENDER = "简约";
    private static final String SUBJECT = "注册用户";
    private static final String SMTP_HOST = "smtp.126.com";
    
    private MailManager(){}
    
	/**
	 * 发送普通邮件
	 */
	public static void sendMail(MailSendRecordDTO mailSendRecordDTO) {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", SMTP_HOST);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.port", "25");				// 服务器端口号
        
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

    	// 3. 发送邮件
		sendMail(session, mailSendRecordDTO);
        
        logger.debug("普通邮件发送成功");
	}
	
	/**
	 * 发送安全邮件
	 * @throws Exception 
	 */
	public static void sendSSLMail(MailSendRecordDTO mailSendRecordDTO) {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", SMTP_HOST);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

		// 3. 发送邮件
		sendMail(session, mailSendRecordDTO);
        
        logger.debug("安全邮件发送成功");
	}
	
	/**
	 * 通过邮件发送验证码
	 * @throws Exception 
	 */
	public static void sendVerifyCodeByMail(String content, String recipient) {
		MailSendRecordDTO mailSendRecordDTO = new MailSendRecordDTO();
		mailSendRecordDTO.setSender(SENDER);
		mailSendRecordDTO.setSubject(SUBJECT);
		mailSendRecordDTO.setContent(content);
		mailSendRecordDTO.setFrom(ACCOUNT);
		mailSendRecordDTO.setRecipient(recipient);
		sendSSLMail(mailSendRecordDTO);
	}
	
	 /**
     * 创建一封只包含文本的简单邮件
     */
    private static void sendMail(Session session, MailSendRecordDTO mailSendRecordDTO) {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        try {
			// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
			message.setFrom(new InternetAddress(mailSendRecordDTO.getFrom(), mailSendRecordDTO.getSender(), "UTF-8"));

			// 3. To: 收件人（可以增加多个收件人、抄送、密送）
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailSendRecordDTO.getRecipient()));
			
			// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
			message.setSubject(mailSendRecordDTO.getSubject(), "UTF-8");

			// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
			message.setContent(mailSendRecordDTO.getContent(), "text/html;charset=UTF-8");

			// 6. 设置发件时间
			message.setSentDate(new Date());

			// 7. 保存设置
			message.saveChanges();
			
			// 8. 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();

			// 9. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			transport.connect(ACCOUNT, PASSWORD);

			// 10. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());

			// 11. 关闭连接
			transport.close();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }
}
