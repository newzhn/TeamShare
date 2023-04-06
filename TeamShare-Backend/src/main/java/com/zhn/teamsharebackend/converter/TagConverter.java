package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.zhn.teamsharebackend.domain.Tag;
import com.zhn.teamsharebackend.domain.dto.TagDto;
import com.zhn.teamsharebackend.domain.vo.TagVo;
import org.springframework.stereotype.Component;

/**
 * @author zhn
 * @version 1.0
 */
@Component
public class TagConverter implements Converter<Tag, TagDto, TagVo> {
    @Override
    public TagDto doToDto(Tag tag) {
        TagDto tagDto = BeanUtil.copyProperties(tag, TagDto.class);
        return tagDto;
    }

    @Override
    public TagVo dtoToVo(TagDto tagDto) {
        TagVo tagVo = BeanUtil.copyProperties(tagDto, TagVo.class,"childrenTags");
        tagVo.setChildrenTags(this.dtoToVoList(tagDto.getChildrenTags()));
        return tagVo;
    }

    @Override
    public TagDto voToDto(TagVo tagVo) {
        TagDto tagDto = BeanUtil.copyProperties(tagVo, TagDto.class);
        tagDto.setChildrenTags(this.voToDtoList(tagVo.getChildrenTags()));
        return tagDto;
    }

    @Override
    public Tag dtoToDo(TagDto tagDto) {
        Tag tag = BeanUtil.copyProperties(tagDto, Tag.class);
        return tag;
    }
}
