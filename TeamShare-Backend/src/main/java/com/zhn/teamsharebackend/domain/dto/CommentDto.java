package com.zhn.teamsharebackend.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class CommentDto {
    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论状态，0是正常，1是被隐藏
     */
    private Integer commentStatus;

    /**
     * 父评论ID
     */
    private Long parentCommentId;

    /**
     * 评论所属的文章ID
     */
    private Long articleId;

    /**
     * 评论人ID
     */
    private Long authorId;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除，0是未删除，1是已删除
     */
    private Integer isDelete;

    /**
     * 回复评论
     */
    private List<CommentDto> replyComments;
}
