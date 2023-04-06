package com.zhn.teamsharebackend.domain.vo;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class CategotyVo {
    /**
     * 类别Id
     */
    private Long categoryId;

    /**
     * 分类名
     */
    private String categoryName;
}
