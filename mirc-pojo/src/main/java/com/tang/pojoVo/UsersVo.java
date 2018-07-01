package com.tang.pojoVo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;

@ApiModel(value = "用户对象",description = "这是用户对象")
public class UsersVo {

    @ApiModelProperty(hidden = true)
    private String id;
    private String token;
    @ApiModelProperty(value = "用户名",name = "username",example = "tangshun",required = true)
    private String username;

    @ApiModelProperty(value = "密码",name = "password",example = "w123456",required = true)
    @JsonIgnore
    private String password;

    @ApiModelProperty(hidden = true)
    private String faceImage;
    private boolean follow;
    /**
     * 昵称
     */
    private String nickname;

    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    @ApiModelProperty(hidden = true)
    private Integer likeCounts;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return face_image
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * @param faceImage
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return fans_counts
     */
    public Integer getFansCounts() {
        return fansCounts;
    }

    /**
     * @param fansCounts
     */
    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    /**
     * @return follow_counts
     */
    public Integer getFollowCounts() {
        return followCounts;
    }

    /**
     * @param followCounts
     */
    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    /**
     * @return like_counts
     */
    public Integer getLikeCounts() {
        return likeCounts;
    }

    /**
     * @param likeCounts
     */
    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }
}