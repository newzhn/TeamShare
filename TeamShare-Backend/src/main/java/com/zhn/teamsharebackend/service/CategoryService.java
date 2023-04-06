package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Category;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.vo.CategotyVo;

import java.util.List;

/**
* @author zhanh
* @description 针对表【category(文章分类表)】的数据库操作Service
* @createDate 2023-04-06 18:02:50
*/
public interface CategoryService extends CRUDTemplate<Category,CategotyVo> {

    Result<List<CategotyVo>> getCategoryList();
}
