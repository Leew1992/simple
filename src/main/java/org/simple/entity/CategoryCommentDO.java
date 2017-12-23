package org.simple.entity;

/**
 * 类别评论信息
 */
public class CategoryCommentDO extends BaseDO {
	private static final long serialVersionUID = 2837806127746303157L;
	private String idCategoryComment;
	private String idCategory;
	private String idComment;

	public String getIdCategoryComment() {
		return idCategoryComment;
	}

	public void setIdCategoryComment(String idCategoryComment) {
		this.idCategoryComment = idCategoryComment;
	}

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

}
