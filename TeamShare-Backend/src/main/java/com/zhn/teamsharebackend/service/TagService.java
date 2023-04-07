package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Tag;
import com.zhn.teamsharebackend.domain.vo.TagVo;

import java.util.List;

/**
* @author zhanh
* @description 针对表【tag(标签表)】的数据库操作Service
* @createDate 2023-03-04 15:58:14
*/
public interface TagService extends ServiceTemplate<Tag, TagVo> {

    /**
     * 获取后台设置的固定tag列表
     * @return
     */
    Result<List<TagVo>> getFinalTagList();
}
