package com.tang.controller;


import com.tang.pojo.Users;
import com.tang.pojoVo.UsersVo;
import com.tang.service.UserService;
import com.tang.utils.MD5Utils;
import com.tang.utils.RedisOperator;
import com.tang.utils.ResponseJSONResult;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户登陆注册的接口",tags = "注册和登陆的controller")
public class RegisterLoginController extends BaseController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户注册",notes = "用户注册的接口")
    @PostMapping("/regiser")
    public ResponseJSONResult register(@RequestBody Users user) throws Exception {
        //判读用户名和密码部为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return ResponseJSONResult.errorMsg("用户名和密码不为空");
        }
        //用户名是否存在
        boolean flag = userService.userNameIsExist(user.getUsername());
        //保存用户注册信息
        if(!flag){
            user.setNickname(user.getUsername());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFollowCounts(0);
            user.setLikeCounts(0);
            user.setFansCounts(0);
            userService.saveUser(user);
        }else{
            return ResponseJSONResult.errorMsg("用户名已存在");
        }
        user.setPassword("");
        UsersVo usersVo = setUserToken(user);
        return ResponseJSONResult.ok(usersVo);
    }

    public UsersVo setUserToken(Users user) {

        String uniqueToken = UUID.randomUUID().toString();
        redisOperator.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000 * 60 * 60);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user, usersVo);
        usersVo.setToken(uniqueToken);

        return usersVo;
    }
    @ApiOperation(value = "用户登陆",notes = "用户登陆的接口")
    @PostMapping("/login")
    public ResponseJSONResult Login(@RequestBody Users user) throws Exception {
        //判读用户名和密码部为空

        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseJSONResult.errorMsg("用户名和密码不为空");
        }
        //用户是否存在
        Users user1 = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (user1 != null) {
            user1.setPassword("");
            UsersVo usersVo = setUserToken(user1);
            return ResponseJSONResult.ok(usersVo);
        }else {
            return ResponseJSONResult.errorMsg("用户名或密码错误");
        }

    }

    @ApiOperation(value = "注销用户", notes = "注销用户的接口")
    @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/loginout")
    public ResponseJSONResult loginout(String userid) {

        redisOperator.del(USER_REDIS_SESSION + ":" + userid);

        return ResponseJSONResult.ok();

    }
}
