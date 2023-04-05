package com.zhn.teamsharebackend.converter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
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

    @Override
    public UserDTO voToDto(UserVo userVo) {
        UserDTO userDTO = BeanUtil.copyProperties(userVo, UserDTO.class, "gender");
        userDTO.setGender("男".equals(userVo.getGender()) ? 0 : 1);
        return userDTO;
    }

    @Override
    public User dtoToDo(UserDTO userDTO) {
        User user = BeanUtil.copyProperties(userDTO, User.class, "teamIds", "tagNames");
        String tagNames = gson.toJson(userDTO.getTagNames() != null ? userDTO.getTagNames() : Collections.emptyList());
        String teamIds = gson.toJson(userDTO.getTeamIds() != null ? userDTO.getTeamIds() : Collections.emptyList());
        user.setTagNames(tagNames);
        user.setTeamIds(teamIds);
        return user;
    }
}
