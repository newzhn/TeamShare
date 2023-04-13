package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.converter.CommentConverter;
import com.zhn.teamsharebackend.domain.Comment;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.CommentDto;
import com.zhn.teamsharebackend.domain.vo.CommentVo;
import com.zhn.teamsharebackend.service.CommentService;
import com.zhn.teamsharebackend.mapper.CommentMapper;
import com.zhn.teamsharebackend.utils.CacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author zhanh
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Resource
    private CommentConverter commentConverter;
    @Resource
    private CacheUtil cacheUtil;

    @Override
    public Result<Boolean> create(Comment comment) {
        boolean save = this.save(comment);
        return Result.ok(save);
    }

    @Override
    public Result<Boolean> delete(long id) {
        return null;
    }

    @Override
    public Result<Boolean> update(Comment comment) {
        return null;
    }

    @Override
    public Result<CommentVo> get(long id) {
        return null;
    }

    @Override
    public CommentVo convert(Comment comment) {
        //递归遍历，将逻辑上为树结构的评论列表扁平化
        CommentDto commentDto = commentConverter.doToDto(comment);
        commentDto.setReplyComments(this.getReplyCommentList(commentDto.getCommentId()));
        this.combineChildren(commentDto);
        return commentConverter.dtoToVo(commentDto);
    }

    @Override
    public Comment getById(Serializable id) {
        return cacheUtil.queryWithPassThrough(CacheConstant.COMMENT,id,Comment.class,super::getById);
    }

    @Override
    public Result<List<CommentVo>> getPublicCommentList(Long articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",articleId)
                .eq("comment_status",0)
                .eq("parent_comment_id",0)
                .orderByDesc("create_time");
        List<Comment> commentList = this.list(wrapper);
        if (commentList == null || commentList.size() == 0) {
            return Result.ok(Collections.emptyList());
        }
        List<CommentVo> commentVoList = commentList.stream().map(this::convert).collect(Collectors.toList());
        return Result.ok(commentVoList);
    }

    private List<CommentDto> getReplyCommentList(Long commentId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_comment_id",commentId)
                .eq("comment_status",0)
                .orderByDesc("create_time");
        List<Comment> commentList = this.list(wrapper);
        if (commentList == null || commentList.size() == 0) {
            return Collections.emptyList();
        }
        List<CommentDto> commentDtoList = commentConverter.doToDtoList(commentList);
        for (CommentDto commentDto : commentDtoList) {
            commentDto.setReplyComments(getReplyCommentList(commentDto.getCommentId()));
        }
        return commentDtoList;
    }

    /**
     * 逻辑上回复评论是呈树状分布的，此方法是将所有子节点评论扁平化存放到一个list集合中去
     * @param commentDtos root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<CommentDto> commentDtos) {
        ArrayList<CommentDto> tempReplys = new ArrayList<>();
        for (CommentDto commentDto : commentDtos) {
            List<CommentDto> replys = commentDto.getReplyComments();
            for(CommentDto reply : replys) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply,tempReplys);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            commentDto.setReplyComments(tempReplys);
            //清除临时存放区数据
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 逻辑上回复评论是呈树状分布的，此方法是将所有子节点评论扁平化存放到一个list集合中去
     * @param commentDto root根节点，blog不为空的对象
     * @return
     */
    private void combineChildren(CommentDto commentDto) {
        ArrayList<CommentDto> tempReplys = new ArrayList<>();
        List<CommentDto> replys = commentDto.getReplyComments();
        for(CommentDto reply : replys) {
            //循环迭代，找出子代，存放在tempReplys中
            recursively(reply,tempReplys);
        }
        //修改顶级节点的reply集合为迭代处理后的集合
        commentDto.setReplyComments(tempReplys);
    }

    /**
     * 递归迭代，剥洋葱
     * @param commentDto 被迭代的对象
     * @return
     */
    private void recursively(CommentDto commentDto,List<CommentDto> tempReplys) {
        //顶节点添加到临时存放集合
        tempReplys.add(commentDto);
        if (commentDto.getReplyComments() != null && commentDto.getReplyComments().size()>0) {
            List<CommentDto> replys = commentDto.getReplyComments();
            for (CommentDto reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply,tempReplys);
                }
                reply.setReplyComments(null);
            }
        }
        commentDto.setReplyComments(null);
    }
}




