package com.zhn.teamsharebackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.converter.ArticleConverter;
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

    @GetMapping("/{id}")
    public Result<ArticleVo> getArticle(@PathVariable("id")Long id) {
        if (id == null || id <= 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"文章信息不存在");
        }
        return articleService.get(id);
    }

    @GetMapping("/list")
    public Result<List<ArticleVo>> getArticleList() {

        return articleService.getArticleListPage();
    }

    @GetMapping("/search/{searchText}")
    public Result<List<ArticleVo>> searchArticleList(@PathVariable("searchText") String searchText) {
        if (StrUtil.isBlank(searchText)) {
            return this.getArticleList();
        }
        return articleService.searchArticleList(searchText);
    }

    @GetMapping("/mustRead")
    public Result<List<ArticleVo>> getMustReadArticleList() {
        return articleService.getMustReadArticleList();
    }
}
