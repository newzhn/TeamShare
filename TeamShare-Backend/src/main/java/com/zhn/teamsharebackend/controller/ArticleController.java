package com.zhn.teamsharebackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zhn.teamsharebackend.converter.ArticleConverter;
import com.zhn.teamsharebackend.domain.Article;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.ArticleDto;
import com.zhn.teamsharebackend.domain.request.ArticleRequest;
import com.zhn.teamsharebackend.domain.vo.ArticleVo;
import com.zhn.teamsharebackend.service.ArticleService;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleConverter articleConverter;

    @PostMapping("/")
    public Result<Boolean> addArticle(@RequestBody ArticleRequest request) {
        //校验
        Result<Boolean> validate = ValidateUtil.articleValidate(request);
        if (validate.getData()) {
            return validate;
        }
        //通过则调用服务
        ArticleDto articleDto = BeanUtil.copyProperties(request, ArticleDto.class);
        return articleService.create(articleConverter.dtoToDo(articleDto));
    }

    @GetMapping("/list")
    public Result<List<ArticleVo>> getArticleList() {

        return articleService.getArticleListPage();
    }
}
