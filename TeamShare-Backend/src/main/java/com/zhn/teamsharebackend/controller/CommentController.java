package com.zhn.teamsharebackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.domain.Comment;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.request.CommentRequest;
import com.zhn.teamsharebackend.domain.vo.CommentVo;
import com.zhn.teamsharebackend.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("/")
    public Result<Boolean> addComment(@RequestBody CommentRequest request) {
        if (ObjectUtil.isEmpty(request)) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR);
        }
        Comment comment = BeanUtil.copyProperties(request, Comment.class);
        return commentService.create(comment);
    }

    @GetMapping("/publicList/{articleId}")
    public Result<List<CommentVo>> getPublicList(@PathVariable("articleId") Long articleId) {
        if (articleId == null || articleId <= 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"该文章评论信息不存在");
        }
        return commentService.getPublicCommentList(articleId);
    }
}
