package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.converter.ArticleConverter;
import com.zhn.teamsharebackend.converter.CategoryConverter;
import com.zhn.teamsharebackend.converter.TagConverter;
import com.zhn.teamsharebackend.converter.UserConverter;
import com.zhn.teamsharebackend.domain.Article;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.ArticleDto;
import com.zhn.teamsharebackend.domain.dto.CategoryDto;
import com.zhn.teamsharebackend.domain.dto.TagDto;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.domain.vo.ArticleVo;
import com.zhn.teamsharebackend.service.ArticleService;
import com.zhn.teamsharebackend.mapper.ArticleMapper;
import com.zhn.teamsharebackend.service.CategoryService;
import com.zhn.teamsharebackend.service.TagService;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author zhanh
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Resource
    private ArticleConverter articleConverter;
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserService userService;
    @Resource
    private TagService tagService;
    @Resource
    private UserConverter userConverter;
    @Resource
    private CategoryConverter categoryConverter;
    @Resource
    private TagConverter tagConverter;

    @Override
    public Result<Boolean> create(Article article) {
        //获取作者信息
        Long authorId = UserHolder.getUser().getUserId();
        //保存数据库
        article.setAuthorId(authorId);
        boolean save = this.save(article);
        return Result.ok(save);
    }

    @Override
    public Result<Boolean> delete(long id) {
        return null;
    }

    @Override
    public Result<Boolean> update(Article article) {
        return null;
    }

    @Override
    public Result<ArticleVo> get(long id) {
        return null;
    }

    @Override
    public ArticleVo convert(Article article) {
        ArticleDto articleDto = articleConverter.doToDto(article);
        Long authorId = articleDto.getAuthorId();
        Long categoryId = articleDto.getCategoryId();
        List<Long> tagIds = articleDto.getTagIds();
        UserDTO author = userConverter.doToDto(userService.getById(authorId));
        CategoryDto category = categoryConverter.doToDto(categoryService.getById(categoryId));
        List<TagDto> tags = tagIds.stream()
                .map(tagId -> tagConverter.doToDto(tagService.getById(tagId))).collect(Collectors.toList());
        articleDto.setAuthor(author);
        articleDto.setCategory(category);
        articleDto.setTags(tags);
        return articleConverter.dtoToVo(articleDto);
    }

    @Override
    public Result<List<ArticleVo>> getArticleListPage() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Article> articleList = this.list(wrapper);
        //遍历查找作者信息，分类信息，标签信息
        List<ArticleVo> articleVoList = articleList.stream().map(this::convert).collect(Collectors.toList());
        return Result.ok(articleVoList);
    }

    @Override
    public Result<List<ArticleVo>> searchArticleList(String searchText) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.like("title",searchText).orderByDesc("create_time");
        List<Article> articleList = this.list(wrapper);
        //遍历查找作者信息，分类信息，标签信息
        List<ArticleVo> articleVoList = articleList.stream().map(this::convert).collect(Collectors.toList());
        return Result.ok(articleVoList);
    }

    @Override
    public Result<List<ArticleVo>> getMustReadArticleList() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        //按阅读量降序排序，取前9条
        wrapper.orderByDesc("reading_volume").last("limit 9");
        List<Article> articleList = this.list(wrapper);
        List<ArticleVo> articleVoList = articleList.stream()
                .map(this::convert).collect(Collectors.toList());
        return Result.ok(articleVoList);
    }
}




