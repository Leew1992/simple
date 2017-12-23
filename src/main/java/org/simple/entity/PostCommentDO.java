package org.simple.entity;


/**
 * 贴子评论信息
 */
public class PostCommentDO extends BaseDO {
	private static final long serialVersionUID = -7526920351699425108L;
	private String idPostComment;
	private String idPost;
	private String idComment;

	public String getIdPostComment() {
		return idPostComment;
	}

	public void setIdPostComment(String idPostComment) {
		this.idPostComment = idPostComment;
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

}
