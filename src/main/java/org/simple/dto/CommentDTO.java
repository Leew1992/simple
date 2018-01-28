package org.simple.dto;

import org.simple.entity.CommentDO;

/**
 * 评论信息DTO
 */
public class CommentDTO extends CommentDO {
	private static final long serialVersionUID = 1243099313400075515L;
	private String idCategory;
	private String title;

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
