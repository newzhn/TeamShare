package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.domain.Article;
import com.zhn.teamsharebackend.service.ArticleService;
import com.zhn.teamsharebackend.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author zhanh
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




