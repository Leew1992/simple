package org.simple.entity;

public class BaseStatDataDO extends BaseDO {
	private static final long serialVersionUID = 8402529339603433225L;
	private Integer userTotal;
	private Integer postTotal;
	private Integer commentTotal;
	private Integer accessTotal;
	private Integer pictureTotal;
	private Integer videoTotal;
	private String accessDate;

	public Integer getUserTotal() {
		return userTotal;
	}

	public void setUserTotal(Integer userTotal) {
		this.userTotal = userTotal;
	}

	public Integer getPostTotal() {
		return postTotal;
	}

	public void setPostTotal(Integer postTotal) {
		this.postTotal = postTotal;
	}

	public Integer getCommentTotal() {
		return commentTotal;
	}

	public void setCommentTotal(Integer commentTotal) {
		this.commentTotal = commentTotal;
	}

	public Integer getAccessTotal() {
		return accessTotal;
	}

	public void setAccessTotal(Integer accessTotal) {
		this.accessTotal = accessTotal;
	}

	public Integer getPictureTotal() {
		return pictureTotal;
	}

	public void setPictureTotal(Integer pictureTotal) {
		this.pictureTotal = pictureTotal;
	}

	public Integer getVideoTotal() {
		return videoTotal;
	}

	public void setVideoTotal(Integer videoTotal) {
		this.videoTotal = videoTotal;
	}

	public String getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}

}
