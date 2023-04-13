package com.zhn.teamsharebackend.domain.request;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class CommentRequest {
    private String avatarUrl;
    private String nickname;
    private String content;
    private Long parentCommentId;
    private Long articleId;
    private Long authorId;
}
