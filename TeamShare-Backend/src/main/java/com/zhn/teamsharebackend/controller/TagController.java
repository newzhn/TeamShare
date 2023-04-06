package com.zhn.teamsharebackend.controller;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.vo.TagVo;
import com.zhn.teamsharebackend.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/finalList")
    public Result<List<TagVo>> getFinalList() {
        return tagService.getFinalTagList();
    }
}
