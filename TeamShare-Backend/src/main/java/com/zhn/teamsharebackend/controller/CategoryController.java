package com.zhn.teamsharebackend.controller;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.vo.CategotyVo;
import com.zhn.teamsharebackend.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<CategotyVo>> getList() {
        return categoryService.getCategoryList();
    }
}
