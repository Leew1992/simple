package org.simple.dao;

import java.util.List;

import org.simple.dto.TreeNode;
import org.simple.entity.ColumnDO;
import org.simple.entity.ColumnPostDO;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnDao {
	
	/**
	 * 获取栏目信息
	 */
    ColumnDO getColumnById(String idColumn);
    
    /**
     * 获取栏目列表
     */
    List<ColumnDO> listAllColumns();
    
    /**
     * 获取栏目列表
     */
    List<ColumnDO> listColumnsByIdParent(String idParent);
    
    /**
	 * 获取栏目树
	 */
	List<TreeNode> getColumnTree();

    /**
     * 获取栏目树
     */
    List<TreeNode> getHasCheckedColumnTreeByIdPost(String idPost);

    /**
     * 保存栏目信息
     */
    void saveColumn(ColumnDO columnDO);

    /**
     * 更新栏目信息
     */
    void updateColumn(ColumnDO columnDO);

    /**
     * 删除栏目信息
     */
    void deleteColumn(String idColumn);
    
    /**
     * 批量批除栏目信息
     */
    void batchDeleteColumns(List<String> idColumns);
    
    /**
     * 获取栏目贴子
     */
    ColumnPostDO getColumnPostByIdPost(String idPost);
    
    /**
     * 保存栏目贴子信息
     */
    void saveColumnPost(ColumnPostDO columnPostDO);

    /**
     * 删除栏目贴子信息
     */
    void deleteColumnPost(String idPost);
    
    /**
     * 批量删除栏目贴子信息
     */
    void batchDeleteColumnPosts(List<String> idPosts);
}
