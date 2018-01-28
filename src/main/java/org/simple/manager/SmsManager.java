package org.simple.manager;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.simple.exception.ServiceException;

/**
 * 短信
 */
public class SmsManager {
	
	private static final Logger logger = Logger.getLogger(SmsManager.class);
	private static final String SMS_UID = "zhangliwei";
	private static final String SMS_KEY = "17e211c04564d9b4348f";
	
	private SmsManager(){}
	
	/**
	 * 发送短信
	 */
	public static void sendSms(String smsMob, String smsText) {
		String utf8SendSmsUrl = "http://utf8.api.smschinese.cn/?Uid=%s&Key=%s&smsMob=%s&smsText=%s";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		utf8SendSmsUrl = String.format(utf8SendSmsUrl, SMS_UID, SMS_KEY, smsMob, smsText);
		logger.info("用户[" + smsMob + "]发送验证码URL：[" + utf8SendSmsUrl + "]");
		HttpGet httpGet = new HttpGet(utf8SendSmsUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		    HttpEntity entity = response.getEntity();
		    InputStream inputStream = entity.getContent();
		    String text = IOUtils.toString(inputStream);
		    logger.info("用户[" + smsMob + "]发送验证码返回结果：[" + text + "]");
		    EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
		    try {
		    	if(response != null) {
		    		response.close();		    		
		    	}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
}
