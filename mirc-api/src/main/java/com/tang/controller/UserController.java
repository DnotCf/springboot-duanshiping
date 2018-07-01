package com.tang.controller;


import com.tang.pojo.Comment;
import com.tang.pojo.UserReport;
import com.tang.pojo.Users;
import com.tang.pojoVo.PublishVo;
import com.tang.pojoVo.UsersVo;
import com.tang.service.UserService;
import com.tang.utils.MD5Utils;
import com.tang.utils.ResponseJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

@RestController
@Api(value = "用户相关业务的接口",tags = "用户相关业务的controller")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @ApiOperation(value = "用户上传", notes = "用户上传的接口")
    @ApiImplicitParam(name = "userid",value = "hello",required = true,dataType = "String",paramType = "query")
    @PostMapping("/uploadFace")
    public ResponseJSONResult Upload( String userid, @RequestParam("file") MultipartFile[] file) throws Exception {
        //文件保存的命名空间
        if (StringUtils.isBlank(userid)) {
            return ResponseJSONResult.errorMsg("上传出错");
        }
        String namespace=NAMESPACE;
        //保存到数据库中的相对路径
        String uploadPathDB=File.separator+userid+File.separator+"face";
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        try {
            if (file != null && file.length > 0) {

                String filename = file[0].getOriginalFilename();

                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径
                    String finalFacePath = namespace + uploadPathDB +File.separator +filename;
                    //设置数据库保存的路径
                    uploadPathDB = uploadPathDB + File.separator + filename;

                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile()!=null || !outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    /*file[0].transferTo(outFile);
*/
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file[0].getInputStream();
                    //apache的IO工具
                    IOUtils.copy(inputStream, fileOutputStream);
                    Users user = new Users();
                    user.setId(userid);
                    user.setFaceImage(uploadPathDB);
                    userService.updateUserInfo(user);
                }
             }else{
                return ResponseJSONResult.errorMsg("上传出错");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return ResponseJSONResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "用户查询的接口",notes = "用户查询的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid",value = "视频发布者的id",dataType = "String",paramType = "query",required = true),
            @ApiImplicitParam(name = "fanId",value = "登陆id",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/query")
    public ResponseJSONResult query(String userid,String fanId) {

        if (StringUtils.isBlank(userid)) {
            return ResponseJSONResult.errorMsg("获取信息失败");
        }

        Users user = userService.query(userid);
        boolean follow = userService.isUserFollowFans(userid, fanId);

        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user, usersVo);
        usersVo.setFollow(follow);
        return ResponseJSONResult.ok(usersVo);
    }

    @ApiOperation(value = "用户发布视频的接口", notes = "用户发布视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginuser", value = "当前登陆的用户id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "videoid", value = "视频id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "publishId", value = "发布视频的用户id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/publisher")
    public ResponseJSONResult publisher(String loginuser, String videoid, String publishId) {

        if (StringUtils.isBlank(videoid) || StringUtils.isBlank(publishId)) {
            return ResponseJSONResult.errorMsg("");
        }
        Users user = userService.query(publishId);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user, usersVo);

        boolean flag = userService.isUerLikeVideo(loginuser, videoid);

        PublishVo p = new PublishVo();
        p.setUsersVo(usersVo);
        p.setUserLikeVideo(flag);

        return ResponseJSONResult.ok(p);
    }


    @ApiOperation(value = "关注的接口",notes = "关注的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid",value = "视频发布者的id",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "loginid",value = "当前登陆的用户id",required = true,dataType = "String",paramType = "query")
    })
    @PostMapping("/beyourfans")
    public ResponseJSONResult beyourFans(String userid,String loginId) {
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(loginId)) {
            return ResponseJSONResult.errorMsg("");
        }
        userService.saveUserFansRealtion(userid, loginId);
        return ResponseJSONResult.ok("关注成功");
    }


    @ApiOperation(value = "取消关注的接口",notes = "取消关注的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid",value = "视频发布者的id",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "loginid",value = "当前登陆的用户id",required = true,dataType = "String",paramType = "query")
    })
    @PostMapping("/notyourfans")
    public ResponseJSONResult notyourFans(String userid,String loginId) {
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(loginId)) {
            return ResponseJSONResult.errorMsg("");
        }
        userService.deleteUserFansRealtion(userid, loginId);
        return ResponseJSONResult.ok("取消成功");
    }

    @ApiOperation(value = "用户举报的接口",notes ="用户举报的接口")
    @PostMapping("/userReport")
    public ResponseJSONResult userReport(@RequestBody UserReport userReport) {

        userService.userReport(userReport);
        return ResponseJSONResult.ok("举报成功");
    }

    @ApiOperation(value = "用户评论的接口", notes = "用户评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fatherCommentId",value = "新增加的用户回复的评论的id",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name="toUserId",value = "新增加的用户回复的人的id",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/userCommet")
    public ResponseJSONResult userComment(@RequestBody Comment comment,String fatherCommentId,String toUserId) {

        userService.saveComment(comment,fatherCommentId,toUserId);
        return ResponseJSONResult.ok("");
    }
}
