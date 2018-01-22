package org.simple.entity;

/**
 * 栏目贴子信息
 */
public class ColumnPostDO extends BaseDO {
	private static final long serialVersionUID = 5799812011864944231L;
	private String idColumnPost;
	private String idColumn;
	private String idPost;

	public String getIdColumnPost() {
		return idColumnPost;
	}

	public void setIdColumnPost(String idColumnPost) {
		this.idColumnPost = idColumnPost;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

}
