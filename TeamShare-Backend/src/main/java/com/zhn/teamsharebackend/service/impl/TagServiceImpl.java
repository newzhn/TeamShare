package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.converter.TagConverter;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Tag;
import com.zhn.teamsharebackend.domain.dto.TagDto;
import com.zhn.teamsharebackend.domain.vo.TagVo;
import com.zhn.teamsharebackend.mapper.TagMapper;
import com.zhn.teamsharebackend.service.TagService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zhanh
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{
    @Resource
    private TagConverter tagConverter;
    @Resource
    private CacheUtil cacheUtil;
    @Resource
    private Gson gson;

    @Override
    public Result<Boolean> create(Tag tag) {
        return null;
    }

    @Override
    public Result<Boolean> delete(long id) {
        return null;
    }

    @Override
    public Result<Boolean> update(Tag tag) {
        return null;
    }

    @Override
    public Result<TagVo> get(long id) {
        return null;
    }

    @Override
    public TagVo convert(Tag tag) {
        TagDto tagDto = tagConverter.doToDto(tag);
        cacheUtil.set(CacheConstant.TAG,tagDto.getTagId(),tagDto);
        return tagConverter.dtoToVo(tagDto);
    }

    @Override
    public Result<List<TagVo>> getFinalTagList() {
        //查询缓存
        String json = cacheUtil.get(CacheConstant.TAG_LIST.getKeyPrefix() + "list");
        //存在直接返回
        if (StrUtil.isNotBlank(json)) {
            List<TagDto> tagDtoList = gson.fromJson(json,new TypeToken<List<TagDto>>() {}.getType());
            return Result.ok(tagConverter.dtoToVoList(tagDtoList));
        }
        //不存在则查询数据库，首先查出所有父标签
        QueryWrapper<Tag> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("is_parent",1);
        List<Tag> parentTagList = this.list(wrapper1);
        List<TagDto> tagDtoList = tagConverter.doToDtoList(parentTagList);
        //处理数据
        for (TagDto tagDto : tagDtoList) {
            List<TagDto> childrenTagList = this.getChildrenTagList(tagDto.getTagId());
            tagDto.setChildrenTags(childrenTagList);
        }
        //保存到Redis
        cacheUtil.set(CacheConstant.TAG_LIST,"list",tagDtoList);
        //返回结果
        return Result.ok(tagConverter.dtoToVoList(tagDtoList));
    }

    private List<TagDto> getChildrenTagList(Long parentTagId) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_tag_id",parentTagId);
        return tagConverter.doToDtoList(this.list(wrapper));
    }
}




