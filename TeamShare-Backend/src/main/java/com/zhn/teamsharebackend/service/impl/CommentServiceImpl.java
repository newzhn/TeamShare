package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.domain.Comment;
import com.zhn.teamsharebackend.service.CommentService;
import com.zhn.teamsharebackend.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author zhanh
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




