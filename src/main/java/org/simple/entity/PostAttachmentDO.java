package org.simple.entity;

/**
 * 贴子附件关联表DO
 */
public class PostAttachmentDO extends BaseDO {
	private static final long serialVersionUID = -7190762769349357284L;
	private String idPostAttachment;
	private String idAttachment;
	private String idPost;

	public String getIdPostAttachment() {
		return idPostAttachment;
	}

	public void setIdPostAttachment(String idPostAttachment) {
		this.idPostAttachment = idPostAttachment;
	}

	public String getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(String idAttachment) {
		this.idAttachment = idAttachment;
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

}
