package com.zhn.teamsharebackend.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class ArticleVo {
    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章阅读量
     */
    private Integer readingVolume;

    /**
     * 文章点赞用户id列表
     */
    private String likes;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 作者信息
     */
    private UserVo author;

    /**
     * 标签列表
     */
    private List<TagVo> tags;
}
