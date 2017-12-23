package org.simple.controller;

import java.text.MessageFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.simple.service.SmsService;

@Controller
@RequestMapping("/sms")
public class SmsController {
	
	protected final Logger logger = Logger.getLogger(getClass());
	private static String VERIFY_CODE_TEMPLATE = "验证码：{0}";
	
	@Autowired
	private SmsService smsService;

	/**
	 * 获取用户信息
	 */
	@RequestMapping("/sendVerifyCode.do")
	@ResponseBody
	public String sendVerifyCode(HttpServletRequest request, String mobilephone) {
		try {
			Random random = new Random();
			String smsMob = MessageFormat.format(VERIFY_CODE_TEMPLATE, random.nextInt(1000000));
			String requestIp = request.getRemoteHost();
			// TODO 暂时不用，烧钱！
			//SmsApi smsApi = new SmsApi();
			//smsApi.sendSms(mobilephone, smsMob);
			smsService.recordSmsSend(mobilephone, smsMob, requestIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SEND_SUCCESS";
	}
}
