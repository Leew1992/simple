package org.simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.simple.dto.ResultDTO;
import org.simple.entity.MailSendRecordDO;
import org.simple.entity.SmsSendRecordDO;
import org.simple.exception.WebException;
import org.simple.manager.MailManager;
import org.simple.manager.SmsManager;
import org.simple.service.MailService;
import org.simple.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/open")
public class OpenController {

	protected final Logger logger = Logger.getLogger(getClass());
	private static final String VERIFY_CODE = "【简约】您正在注册账号，验证码：%s，请在30钟内使用，谢谢。";
	private static final String SENDER = "简约";
	private static final String SUBJECT = "账号注册";
	private static final String FROM = "lwzhang1992@126.com";
	private static final String TYPE_VERIFY_CODE = "verify_code";
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private MailService mailService;

	/**
	 * 发送短信验证码
	 */
	@RequestMapping("/sendVerifyCodeBySms.do")
	@ResponseBody
	public ResultDTO sendVerifyCodeBySms(HttpServletRequest request, String mobilephone) {
		try {
			int randomValue = (int)((Math.random()*9+1)*100000);
			String smsContent = String.format(VERIFY_CODE, randomValue);
			SmsSendRecordDO smsSendRecordDO = new SmsSendRecordDO();
			smsSendRecordDO.setMobilephone(mobilephone);
			smsSendRecordDO.setSmsContent(smsContent);
			smsSendRecordDO.setExtractWord(String.valueOf(randomValue));
			smsSendRecordDO.setType(TYPE_VERIFY_CODE);
			smsSendRecordDO.setRemoteAddress(request.getRemoteHost());
			smsService.saveSmsSendRecord(smsSendRecordDO);
			SmsManager.sendSms(mobilephone, smsContent);
		} catch (Exception e) {
			throw new WebException(e);
		}
		return new ResultDTO(true,"发送成功");
	}
	
	/**
	 * 发送邮件验证码
	 */
	@RequestMapping("/sendVerifyCodeByMail.do")
	@ResponseBody
	public ResultDTO sendVerifyCodeByMail(HttpServletRequest request, String email) {
		try {
			int randomValue = (int)((Math.random()*9+1)*100000);
			String content = String.format(VERIFY_CODE, randomValue);
			MailSendRecordDO mailSendRecordDO = new MailSendRecordDO();
			mailSendRecordDO.setSender(SENDER);
			mailSendRecordDO.setSubject(SUBJECT);
			mailSendRecordDO.setContent(content);
			mailSendRecordDO.setExtractWord(String.valueOf(randomValue));
			mailSendRecordDO.setFrom(FROM);
			mailSendRecordDO.setRecipient(email);
			mailSendRecordDO.setType(TYPE_VERIFY_CODE);
			mailSendRecordDO.setRemoteAddress(request.getRemoteHost());
			mailService.saveMailSendRecord(mailSendRecordDO);
			MailManager.sendVerifyCodeByMail(content, email);
		} catch (Exception e) {
			throw new WebException(e);
		}
		return new ResultDTO(true,"发送成功");
	}
}
