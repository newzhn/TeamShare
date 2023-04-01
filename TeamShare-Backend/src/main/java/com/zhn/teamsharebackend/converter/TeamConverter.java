package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.domain.dto.TeamDTO;
import com.zhn.teamsharebackend.domain.vo.TeamVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Component
public class TeamConverter implements Converter<Team, TeamDTO, TeamVo> {
    @Resource
    private Gson gson;
    @Resource
    private UserConverter userConverter;

    @Override
    public TeamDTO doToDto(Team team) {
        TeamDTO teamDto = BeanUtil.copyProperties(team, TeamDTO.class,"memberIds");
        List<Long> memberIds = gson.fromJson(team.getMemberIds(),new TypeToken<List<Long>>() {}.getType());
        teamDto.setMemberIds(memberIds != null ? memberIds : Collections.emptyList());
        return teamDto;
    }

    @Override
    public TeamVo dtoToVo(TeamDTO teamDTO) {
        TeamVo teamVo = BeanUtil.copyProperties(teamDTO, TeamVo.class);
        teamVo.setCaptain(userConverter.dtoToVo(teamDTO.getCaptain()));
        teamVo.setMembers(userConverter.dtoToVoList(teamDTO.getMembers()));
        return teamVo;
    }
}
