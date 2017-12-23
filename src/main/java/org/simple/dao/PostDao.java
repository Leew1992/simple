package org.simple.dao;

import java.util.List;

import org.simple.dto.PostDTO;
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
    List<PostDO> pagingPosts(PostDTO postDTO);
    
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
}