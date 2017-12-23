package org.simple.entity;


/**
 * 短信发送统计信息
 */
public class SmsSendStatDO extends BaseDO {
	private static final long serialVersionUID = 5384666466884554208L;
	private String idSmsSendStat;
	private String mobilephone;
	private Integer sendCount;

	public String getIdSmsSendStat() {
		return idSmsSendStat;
	}

	public void setIdSmsSendStat(String idSmsSendStat) {
		this.idSmsSendStat = idSmsSendStat;
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
