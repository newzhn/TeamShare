package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.domain.vo.UserVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Component
public class UserConverter implements Converter<User, UserDTO, UserVo> {
    @Resource
    private Gson gson;

    @Override
    public UserDTO doToDto(User user) {
        UserDTO userDto = BeanUtil.copyProperties(user, UserDTO.class,"teamIds","tagNames");
        List<String> tagNames = gson.fromJson(user.getTagNames(),new TypeToken<List<String>>() {}.getType());
        List<Long> teamIds = gson.fromJson(user.getTeamIds(),new TypeToken<List<Long>>() {}.getType());
        userDto.setTagNames(tagNames != null ? tagNames : Collections.emptyList());
        userDto.setTeamIds(teamIds != null ? teamIds : Collections.emptyList());
        return userDto;
    }

    @Override
    public UserVo dtoToVo(UserDTO userDto) {
        UserVo userVo = BeanUtil.copyProperties(userDto, UserVo.class,"gender");
        userVo.setGender(userDto.getGender() == 0 ? "女" : "男");
        return userVo;
    }
}
