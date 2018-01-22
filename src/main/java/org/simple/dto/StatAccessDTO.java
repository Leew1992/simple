package org.simple.dto;

import java.io.Serializable;

public class StatAccessDTO implements Serializable {
	private static final long serialVersionUID = -1222026139527708665L;
	private Integer userTotal;
	private Integer roleTotal;
	private Integer menuTotal;
	private Integer postTotal;
	private Integer commentTotal;

	public Integer getUserTotal() {
		return userTotal;
	}

	public void setUserTotal(Integer userTotal) {
		this.userTotal = userTotal;
	}

	public Integer getRoleTotal() {
		return roleTotal;
	}

	public void setRoleTotal(Integer roleTotal) {
		this.roleTotal = roleTotal;
	}

	public Integer getMenuTotal() {
		return menuTotal;
	}

	public void setMenuTotal(Integer menuTotal) {
		this.menuTotal = menuTotal;
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

}
