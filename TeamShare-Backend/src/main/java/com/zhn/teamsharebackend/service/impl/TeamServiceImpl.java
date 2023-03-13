package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.service.TeamService;
import com.zhn.teamsharebackend.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author zhanh
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




