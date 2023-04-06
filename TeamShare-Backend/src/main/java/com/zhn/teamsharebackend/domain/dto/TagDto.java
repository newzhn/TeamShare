package com.zhn.teamsharebackend.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class TagDto {
    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 父标签Id
     */
    private Long parentTagId;

    /**
     * 是否是父标签，0为不是，1为是
     */
    private Integer isParent;

    /**
     * 是否删除，0是未删除，1是已删除
     */
    private Integer isDelete;

    /**
     * 注册事件
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 子标签列表
     */
    private List<TagDto> childrenTags;
}
