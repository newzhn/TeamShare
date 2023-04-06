package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.domain.Category;
import com.zhn.teamsharebackend.service.CategoryService;
import com.zhn.teamsharebackend.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author zhanh
* @description 针对表【category(文章分类表)】的数据库操作Service实现
* @createDate 2023-04-06 18:02:50
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




