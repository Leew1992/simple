package org.simple.entity;

/**
 * 短信发送记录
 */
public class MailSendRecordDO extends BaseDO {
	private static final long serialVersionUID = 3617008030467508732L;
	private String idMailSendRecord;
	private String sender;
	private String subject;
	private String content;
	private String extractWord;
	private String from;
	private String recipient;
	private String type;
	private String remoteAddress;

	public String getIdMailSendRecord() {
		return idMailSendRecord;
	}

	public void setIdMailSendRecord(String idMailSendRecord) {
		this.idMailSendRecord = idMailSendRecord;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getExtractWord() {
		return extractWord;
	}

	public void setExtractWord(String extractWord) {
		this.extractWord = extractWord;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
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
