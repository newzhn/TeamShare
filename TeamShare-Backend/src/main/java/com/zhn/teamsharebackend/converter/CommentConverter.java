package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.zhn.teamsharebackend.domain.Comment;
import com.zhn.teamsharebackend.domain.dto.CommentDto;
import com.zhn.teamsharebackend.domain.vo.CommentVo;
import org.springframework.stereotype.Component;

/**
 * @author zhn
 * @version 1.0
 */
@Component
public class CommentConverter implements Converter<Comment, CommentDto, CommentVo> {
    @Override
    public CommentDto doToDto(Comment comment) {
        return BeanUtil.copyProperties(comment, CommentDto.class);
    }

    @Override
    public CommentVo dtoToVo(CommentDto commentDto) {
        CommentVo commentVo = BeanUtil.copyProperties(commentDto, CommentVo.class,"replyComments");
        commentVo.setReplyComments(this.dtoToVoList(commentDto.getReplyComments()));
        return commentVo;
    }

    @Override
    public CommentDto voToDto(CommentVo commentVo) {
        CommentDto commentDto = BeanUtil.copyProperties(commentVo, CommentDto.class,"replyComments");
        commentDto.setReplyComments(this.voToDtoList(commentVo.getReplyComments()));
        return commentDto;
    }

    @Override
    public Comment dtoToDo(CommentDto commentDto) {
        return BeanUtil.copyProperties(commentDto,Comment.class);
    }
}
