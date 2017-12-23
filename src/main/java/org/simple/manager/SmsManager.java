package org.simple.manager;

import java.io.InputStream;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 短信发送
 */
public class SmsManager {
	protected final Logger logger = Logger.getLogger(getClass());
	
	// utf8格式的短信发送URL
	private static String UTF8_SEND_SMS_URL = "http://utf8.api.smschinese.cn/?Uid={0}&Key={1}&smsMob={2}&smsText={3}";
	private static final String SMS_UID = "zhangliwei";
	private static final String SMS_KEY = "17e211c04564d9b4348f";
	
	/**
	 * 发送短信
	 */
	public void sendSms(String smsMob, String smsText) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String sendSmsUrl = MessageFormat.format(UTF8_SEND_SMS_URL, SMS_UID, SMS_KEY, smsMob, smsText);
		logger.info("用户[" + smsMob + "]发送验证码URL：[" + sendSmsUrl + "]");
		HttpGet httpGet = new HttpGet(sendSmsUrl);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		try {
		    HttpEntity entity = response.getEntity();
		    InputStream inputStream = entity.getContent();
		    String text = IOUtils.toString(inputStream);
		    logger.info("用户[" + smsMob + "]发送验证码返回结果：[" + text + "]");
		    EntityUtils.consume(entity);
		} finally {
		    response.close();
		}
	}
	
}
