package com.tang.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class VideosVo {

    private String id;

    private String userId;

    private String audioId;


    private String videoDesc;


    private String videoPath;


    private Float videoSeconds;


    private Integer videoWidth;


    private Integer videoHeight;


    private String coverPath;


    private Long likeCounts;

    private Integer status;

    /**
     * 1.发布成功,2.禁止播放
     */

    private Date createTime;

    private String faceImage;

    private String nickname;

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return audio_id
     */
    public String getAudioId() {
        return audioId;
    }

    /**
     * @param audioId
     */
    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    /**
     * @return video_desc
     */
    public String getVideoDesc() {
        return videoDesc;
    }

    /**
     * @param videoDesc
     */
    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    /**
     * @return video_path
     */
    public String getVideoPath() {
        return videoPath;
    }

    /**
     * @param videoPath
     */
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    /**
     * @return video_seconds
     */
    public Float getVideoSeconds() {
        return videoSeconds;
    }

    /**
     * @param videoSeconds
     */
    public void setVideoSeconds(Float videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    /**
     * @return video_width
     */
    public Integer getVideoWidth() {
        return videoWidth;
    }

    /**
     * @param videoWidth
     */
    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    /**
     * @return video_height
     */
    public Integer getVideoHeight() {
        return videoHeight;
    }

    /**
     * @param videoHeight
     */
    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    /**
     * 获取视频首页展示图片路径
     *
     * @return cover_path - 视频首页展示图片路径
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     * 设置视频首页展示图片路径
     *
     * @param coverPath 视频首页展示图片路径
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     * @return like_counts
     */
    public Long getLikeCounts() {
        return likeCounts;
    }

    /**
     * @param likeCounts
     */
    public void setLikeCounts(Long likeCounts) {
        this.likeCounts = likeCounts;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取1.发布成功,2.禁止播放
     *
     * @return create_time - 1.发布成功,2.禁止播放
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置1.发布成功,2.禁止播放
     *
     * @param createTime 1.发布成功,2.禁止播放
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}