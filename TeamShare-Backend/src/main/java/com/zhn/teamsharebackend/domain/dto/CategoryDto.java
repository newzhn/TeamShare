package com.zhn.teamsharebackend.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class CategoryDto {
    /**
     * 类别Id
     */
    private Long categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否被删除
     */
    private Integer isDelete;
}
