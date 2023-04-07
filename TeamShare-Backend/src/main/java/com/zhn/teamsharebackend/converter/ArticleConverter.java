package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhn.teamsharebackend.domain.Article;
import com.zhn.teamsharebackend.domain.dto.ArticleDto;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
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
    private CategoryConverter categoryConverter;
    @Resource
    private Gson gson;

    @Override
    public ArticleDto doToDto(Article article) {
        ArticleDto articleDto = BeanUtil.copyProperties(article, ArticleDto.class);
        List<Long> likes = gson.fromJson(article.getLikes(),new TypeToken<List<Long>>() {}.getType());
        List<Long> tagIds = gson.fromJson(article.getTagIds(),new TypeToken<List<Long>>() {}.getType());
        articleDto.setLikeIds(likes);
        articleDto.setTagIds(tagIds);
        return articleDto;
    }

    @Override
    public ArticleVo dtoToVo(ArticleDto articleDto) {
        ArticleVo articleVo = BeanUtil.copyProperties(articleDto, ArticleVo.class,"author","tags");
        articleVo.setAuthor(userConverter.dtoToVo(articleDto.getAuthor()));
        articleVo.setTags(tagConverter.dtoToVoList(articleDto.getTags()));
        articleVo.setCategory(categoryConverter.dtoToVo(articleDto.getCategory()));
        articleVo.setLikes(userConverter.dtoToVoList(articleDto.getLikes()));
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
        articleDto.setTagIds(tagIds);
        articleDto.setCategory(categoryConverter.voToDto(articleVo.getCategory()));
        articleDto.setCategoryId(articleDto.getCategoryId());
        List<UserDTO> likes = userConverter.voToDtoList(articleVo.getLikes());
        List<Long> likeIds = likes.stream().map(UserDTO::getUserId).collect(Collectors.toList());
        articleDto.setLikes(likes);
        articleDto.setLikeIds(likeIds);
        return articleDto;
    }

    @Override
    public Article dtoToDo(ArticleDto articleDto) {
        Article article = BeanUtil.copyProperties(articleDto, Article.class);
        List<Long> likeIds = articleDto.getLikeIds();
        List<Long> tagIds = articleDto.getTagIds();
        article.setCategoryId(articleDto.getCategoryId());
        article.setLikes(likeIds == null ? null : gson.toJson(likeIds));
        article.setTagIds(likeIds == null ? null : gson.toJson(tagIds));
        return article;
    }
}
