package com.tang.controller;


import com.tang.pojo.Bgm;
import com.tang.service.BgmService;
import com.tang.utils.ResponseJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "背景音乐的接口",tags = "背景音乐的controller")
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;


    @ApiOperation(value = "获取背景音乐列表",notes = "获取背景音乐列表的接口")
    @PostMapping("/list")
    public ResponseJSONResult bgmList() {

        List<Bgm> list = bgmService.queryBgmList();
        return ResponseJSONResult.ok(list);
    }

}
