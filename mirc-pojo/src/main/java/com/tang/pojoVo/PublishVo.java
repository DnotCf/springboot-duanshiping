package com.tang.pojoVo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "发布对象",description = "用户发布对象")
public class PublishVo {

    private UsersVo usersVo;
    private boolean userLikeVideo;

    public UsersVo getUsersVo() {
        return usersVo;
    }

    public void setUsersVo(UsersVo usersVo) {
        this.usersVo = usersVo;
    }

    public boolean isUserLikeVideo() {
        return userLikeVideo;
    }

    public void setUserLikeVideo(boolean userLikeVideo) {
        this.userLikeVideo = userLikeVideo;
    }
}