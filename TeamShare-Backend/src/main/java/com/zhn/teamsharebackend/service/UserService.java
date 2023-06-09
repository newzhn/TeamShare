package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.vo.UserVo;

import java.util.List;

/**
* @author zhanh
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-03-04 15:58:14
*/
public interface UserService extends ServiceTemplate<User, UserVo> {

    Result<UserVo> getLoginUser(String token);

    Result<Boolean> updateUser(UserVo userVo,String token);

    Result<List<UserVo>> getRecommendUserList();
}
