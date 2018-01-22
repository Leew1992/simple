package org.simple.entity;


/**
 * 短信统计信息
 */
public class SmsStatDO extends BaseDO {
	private static final long serialVersionUID = 5384666466884554208L;
	private String idSmsStat;
	private String mobilephone;
	private Integer sendCount;

	public String getIdSmsStat() {
		return idSmsStat;
	}

	public void setIdSmsStat(String idSmsStat) {
		this.idSmsStat = idSmsStat;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

}
