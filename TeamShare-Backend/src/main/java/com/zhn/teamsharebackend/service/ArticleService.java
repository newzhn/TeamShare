package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Article;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.vo.ArticleVo;

import java.util.List;

/**
* @author zhanh
* @description 针对表【article(文章表)】的数据库操作Service
* @createDate 2023-03-04 15:58:14
*/
public interface ArticleService extends ServiceTemplate<Article, ArticleVo> {

    Result<List<ArticleVo>> getArticleListPage();

    Result<List<ArticleVo>> searchArticleList(String searchText);

    Result<List<ArticleVo>> getMustReadArticleList();

    Result<List<ArticleVo>> getRecommendArticleList();

    Result<List<ArticleVo>> getRecentPosts();
}
