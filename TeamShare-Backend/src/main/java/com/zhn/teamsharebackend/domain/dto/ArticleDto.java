package com.zhn.teamsharebackend.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class ArticleDto {
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
     * 文章状态，0是草稿，1是已发布，2是精选等
     */
    private Integer articleStatus;

    /**
     * 文章阅读量
     */
    private Integer readingVolume;

    /**
     * 文章点赞用户id列表
     */
    private List<Long> likeIds;

    /**
     * 文章分类Id
     */
    private Long categoryId;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * json格式标签Id列表
     */
    private List<Long> tagIds;

    /**
     * 发布时间
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
     * 分类信息
     */
    private CategoryDto category;

    /**
     * 作者信息
     */
    private UserDTO author;

    /**
     * 标签列表
     */
    private List<TagDto> tags;

    /**
     * 点赞用户信息
     */
    private List<UserDTO> likes;
}
