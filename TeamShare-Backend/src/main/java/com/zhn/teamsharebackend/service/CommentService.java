package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.vo.CommentVo;

import java.util.List;

/**
* @author zhanh
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2023-03-04 15:58:14
*/
public interface CommentService extends ServiceTemplate<Comment,CommentVo> {

    Result<List<CommentVo>> getPublicCommentList(Long articleId);

}
