package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.entity.PostAttachmentDO;
import org.simple.entity.PostCommentDO;
import org.simple.entity.PostDO;
import org.springframework.stereotype.Repository;

/**
 * 贴子Dao接口
 */
@Repository
public interface PostDao {
    
    /**
     * 获取贴子信息
     */
    PostDO getPostById(String idPost);
    
    /**
     * 列出贴子信息
     */
    List<PostDO> listPostsByIdColumn(String idColumn);
    
    /**
     * 获取贴子分页信息
     */
    List<PostDO> pagingPosts(Map<String, Object> paramMap);
    
    /**
     * 统计贴子数量
     */
    Integer countPostsBasedFuzzy(String createdDate);
    
    /**
     * 统计贴子数量
     */
    Integer countPostsBasedAccurate(List<String> createdDates);
    
    /**
     * 保存贴子信息
     */
    void savePost(PostDO postDO);
    
    /**
     * 更新贴子信息
     */
    void updatePost(PostDO postDO);
    
    /**
     * 删除贴子信息
     */
    void deletePost(String idPost);
    
    /**
     * 批量删除贴子信息
     */
    void batchDeletePosts(List<String> idPosts);
    
    /**
     * 获取贴子评论列表
     */
    List<PostCommentDO> listPostCommentsByIdPost(String idPost);
    
    /**
     * 保存贴子评论信息
     */
    void savePostComment(PostCommentDO postCommentDO);
    
    /**
     * 删除贴子评论信息
     */
    void deletePostComment(String idPostComment);
    
    /**
     * 批量删除贴子评论信息
     */
    void batchDeletePostComments(List<String> idPostComments);
    
    /**
     * 获取贴子评论列表
     */
    List<PostCommentDO> listPostAttachmentsByIdPost(String idPost);
    
    /**
     * 保存贴子评论信息
     */
    void savePostAttachment(PostAttachmentDO postCommentDO);
    
    /**
     * 删除贴子评论信息
     */
    void deletePostAttachment(String idPostAttachment);
    
    /**
     * 删除贴子附件信息
     */
    void deletePostAttachmentByAttachment(String idAttachment);
    
    /**
     * 批量删除贴子附件信息
     */
    void batchDeletePostAttachments(List<String> idPostAttachments);
}