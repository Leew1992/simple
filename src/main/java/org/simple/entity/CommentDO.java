package org.simple.entity;


/**
 * 评论信息
 */
public class CommentDO extends BaseDO {
	private static final long serialVersionUID = 5323213913180105033L;
	private String idComment;
	private String idPost;
	private String idUser;
	private String content;
	private Integer favorNum;
	private Integer orderNo;
	private String status;

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFavorNum() {
		return favorNum;
	}

	public void setFavorNum(Integer favorNum) {
		this.favorNum = favorNum;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
