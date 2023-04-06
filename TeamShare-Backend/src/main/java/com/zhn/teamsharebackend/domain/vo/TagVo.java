package com.zhn.teamsharebackend.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class TagVo {
    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 子标签列表
     */
    private List<TagVo> childrenTags;
}
