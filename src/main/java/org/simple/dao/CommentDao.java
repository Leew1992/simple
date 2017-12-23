package org.simple.dao;

import java.util.List;

import org.simple.dto.CommentDTO;
import org.simple.entity.CommentDO;
import org.springframework.stereotype.Repository;

/**
 * 评论Dao
 */
@Repository
public interface CommentDao {
    
    /**
     * 获取评论信息
     */
    CommentDO getCommentById(String idComment);
    
    List<CommentDO> listCommentsByIds(List<String> idComments);
    
    /**
     * 获取所有评论信息
     */
    List<CommentDO> listAllComments();
    
    /**
     * 获取评论分页信息
     */
    List<CommentDTO> pagingComments(CommentDTO commentDTO);
    
    /**
     * 保存评论信息
     */
    void saveComment(CommentDO commentDO);
    
    /**
     * 更新评论信息
     */
    void updateComment(CommentDO commentDO);
    
    /**
     * 置顶评论
     */
    void stickCommentById(String idComment);
    
    /**
     * 删除评论信息
     */
    void deleteComment(String idComment);
    
    /**
     * 批量删除评论信息
     */
    void batchDeleteComments(List<String> idComments);
    
    
}