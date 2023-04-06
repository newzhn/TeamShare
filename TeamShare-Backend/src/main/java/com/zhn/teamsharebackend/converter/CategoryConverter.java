package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.zhn.teamsharebackend.domain.Category;
import com.zhn.teamsharebackend.domain.dto.CategoryDto;
import com.zhn.teamsharebackend.domain.vo.CategotyVo;
import org.springframework.stereotype.Component;

/**
 * @author zhn
 * @version 1.0
 */
@Component
public class CategoryConverter implements Converter<Category, CategoryDto, CategotyVo> {
    @Override
    public CategoryDto doToDto(Category category) {
        CategoryDto categoryDto = BeanUtil.copyProperties(category, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public CategotyVo dtoToVo(CategoryDto categoryDto) {
        CategotyVo categotyVo = BeanUtil.copyProperties(categoryDto, CategotyVo.class);
        return categotyVo;
    }

    @Override
    public CategoryDto voToDto(CategotyVo categotyVo) {
        CategoryDto categoryDto = BeanUtil.copyProperties(categotyVo, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public Category dtoToDo(CategoryDto categoryDto) {
        Category category = BeanUtil.copyProperties(categoryDto, Category.class);
        return category;
    }
}
