package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.converter.CategoryConverter;
import com.zhn.teamsharebackend.domain.Category;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.CategoryDto;
import com.zhn.teamsharebackend.domain.vo.CategotyVo;
import com.zhn.teamsharebackend.service.CategoryService;
import com.zhn.teamsharebackend.mapper.CategoryMapper;
import com.zhn.teamsharebackend.utils.CacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author zhanh
* @description 针对表【category(文章分类表)】的数据库操作Service实现
* @createDate 2023-04-06 18:02:50
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Resource
    private CategoryConverter categoryConverter;
    @Resource
    private CacheUtil cacheUtil;
    @Resource
    private Gson gson;

    @Override
    public Result<Boolean> create(Category category) {
        return null;
    }

    @Override
    public Result<Boolean> delete(long id) {
        return null;
    }

    @Override
    public Result<Boolean> update(Category category) {
        return null;
    }

    @Override
    public Result<CategotyVo> get(long id) {
        return null;
    }

    @Override
    public CategotyVo convert(Category category) {
        CategoryDto categoryDto = categoryConverter.doToDto(category);
        cacheUtil.set(CacheConstant.CATEGORY,categoryDto.getCategoryId(),categoryDto);
        return categoryConverter.dtoToVo(categoryDto);
    }

    @Override
    public Result<List<CategotyVo>> getCategoryList() {
        //先查询缓存
        String json = cacheUtil.get(CacheConstant.CATEGORY_LIST.getKeyPrefix() + "list");
        //有则直接返回
        if (StrUtil.isNotBlank(json)) {
            List<CategoryDto> categoryDtoList = gson.fromJson(json,new TypeToken<List<CategoryDto>>() {}.getType());
            return Result.ok(categoryConverter.dtoToVoList(categoryDtoList));
        }
        //没有则查询数据库
        List<Category> categoryList = this.list();
        //加工数据
        List<CategotyVo> categoryVoList = categoryList.stream().map(this::convert).collect(Collectors.toList());
        //保存到Redis
        List<CategoryDto> categoryDtoList = categoryConverter.voToDtoList(categoryVoList);
        cacheUtil.set(CacheConstant.CATEGORY_LIST,"list",categoryDtoList);
        //返回结果
        return Result.ok(categoryVoList);
    }
}




