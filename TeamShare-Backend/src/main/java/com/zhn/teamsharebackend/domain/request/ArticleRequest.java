package com.zhn.teamsharebackend.domain.request;

import lombok.Data;

import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class ArticleRequest {
    private String title;
    private String content;
    private Long categoryId;
    private List<Long> tagIds;
}
