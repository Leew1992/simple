package org.simple.entity;


/**
 * 短信发送记录信息
 */
public class SmsSendRecordDO extends BaseDO {
	private static final long serialVersionUID = -2879750185913671378L;
	private String idSmsSendRecord;
	private String mobilephone;
	private String smsContent;
	private String requestIp;

	public String getIdSmsSendRecord() {
		return idSmsSendRecord;
	}

	public void setIdSmsSendRecord(String idSmsSendRecord) {
		this.idSmsSendRecord = idSmsSendRecord;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

}
