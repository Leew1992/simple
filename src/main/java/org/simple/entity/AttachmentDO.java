package org.simple.entity;


/**
 * 附件信息
 */
public class AttachmentDO extends BaseDO {
	private static final long serialVersionUID = -130890001948927749L;
	private String idAttachment;
	private String attachName;
	private String attachPath;
	private Integer attachSize;
	private String mediaType;
	private String attachSource;
	
	public String getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(String idAttachment) {
		this.idAttachment = idAttachment;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public Integer getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Integer attachSize) {
		this.attachSize = attachSize;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getAttachSource() {
		return attachSource;
	}

	public void setAttachSource(String attachSource) {
		this.attachSource = attachSource;
	}

}
