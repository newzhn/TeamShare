package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.zhn.teamsharebackend.domain.Article;
import com.zhn.teamsharebackend.domain.dto.ArticleDto;
import com.zhn.teamsharebackend.domain.vo.ArticleVo;
import com.zhn.teamsharebackend.domain.vo.TagVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhn
 * @version 1.0
 */
@Component
public class ArticleConverter implements Converter<Article, ArticleDto, ArticleVo>{
    @Resource
    private UserConverter userConverter;
    @Resource
    private TagConverter tagConverter;
    @Resource
    private Gson gson;

    @Override
    public ArticleDto doToDto(Article article) {
        return BeanUtil.copyProperties(article, ArticleDto.class);
    }

    @Override
    public ArticleVo dtoToVo(ArticleDto articleDto) {
        ArticleVo articleVo = BeanUtil.copyProperties(articleDto, ArticleVo.class,"author","tags");
        articleVo.setAuthor(userConverter.dtoToVo(articleDto.getAuthor()));
        articleVo.setTags(tagConverter.dtoToVoList(articleDto.getTags()));
        return articleVo;
    }

    @Override
    public ArticleDto voToDto(ArticleVo articleVo) {
        ArticleDto articleDto = BeanUtil.copyProperties(articleVo, ArticleDto.class,"author","tags");
        articleDto.setAuthor(userConverter.voToDto(articleVo.getAuthor()));
        articleDto.setAuthorId(articleDto != null ? articleDto.getAuthor().getUserId() : null);
        List<TagVo> tags = articleVo.getTags();
        List<Long> tagIds = tags.stream().map(TagVo::getTagId).collect(Collectors.toList());
        articleDto.setTags(tagConverter.voToDtoList(tags));
        articleDto.setTagIds(gson.toJson(tagIds));
        return articleDto;
    }

    @Override
    public Article dtoToDo(ArticleDto articleDto) {
        return BeanUtil.copyProperties(articleDto, Article.class);
    }
}
