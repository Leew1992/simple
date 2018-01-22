package org.simple.entity;

/**
 * 短信记录信息
 */
public class SmsSendRecordDO extends BaseDO {
	private static final long serialVersionUID = -2879750185913671378L;
	private String idSmsSendRecord;
	private String mobilephone;
	private String smsContent;
	private String extractWord;
	private String type;
	private String remoteAddress;

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

	public String getExtractWord() {
		return extractWord;
	}

	public void setExtractWord(String extractWord) {
		this.extractWord = extractWord;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

}
