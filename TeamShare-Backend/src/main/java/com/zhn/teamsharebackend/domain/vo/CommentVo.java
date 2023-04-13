package com.zhn.teamsharebackend.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class CommentVo {
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
     * 父评论ID
     */
    private Long parentCommentId;

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
     * 回复评论
     */
    private List<CommentVo> replyComments;
}
