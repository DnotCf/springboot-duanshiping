package com.tang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_report")
@ApiModel(value = "用户举报对象",description = "用户举报视频的对象")
public class UserReport {
    @Id
    private String id;

    @ApiModelProperty(value = "被举报用户的id",name = "dealUserId",example = "IAWDASDAA",required = true)
    @Column(name = "deal_user_id")
    private String dealUserId;

    @ApiModelProperty(value = "被举报的视频id",name = "dealVideoId",example = "AFDAFSFSDFSD",required = true)
    @Column(name = "deal_video_id")
    private String dealVideoId;

    @ApiModelProperty(value = "举报标题",name = "title",example = "低俗",required = true)
    private String title;

    @ApiModelProperty(value = "举报内容", name = "content", example = "内容太低俗", required = true)
    private String content;

    @ApiModelProperty(value = "举报用户的id",name = "userid",example = "AADSASD",required = true)
    private String userid;

    @ApiModelProperty(value = "举报时间",name = "createDate",example = "2018-8-8",required = true)
    @Column(name = "create_date")
    private Date createDate;

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
     * @return deal_user_id
     */
    public String getDealUserId() {
        return dealUserId;
    }

    /**
     * @param dealUserId
     */
    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    /**
     * @return deal_video_id
     */
    public String getDealVideoId() {
        return dealVideoId;
    }

    /**
     * @param dealVideoId
     */
    public void setDealVideoId(String dealVideoId) {
        this.dealVideoId = dealVideoId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}