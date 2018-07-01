package com.tang.service;

import com.tang.pojo.Users;
import com.tang.pojo.Videos;
import com.tang.utils.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoService {


    /**
     * 保存是视频
     * @param video
     */
    public String save(Videos video);

    /**
     * 保存封面
     * @param videoId
     * @param coverUrl
     */
    public void updateCover(String videoId, String coverUrl);

    /**
     * 查询视频地址
     * @param videoId
     * @return
     */
    public Videos vidoeUrl(String videoId);

    /**
     * 分页搜搜索查询视频
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult getAllVideos(Integer isSaveRecord, Videos video,Integer page, Integer pageSize);

    /**
     * 热搜词查询
     * @return
     */
    public List<String> hotWords();

    /**
     * 用户点赞
     * @param userid
     * @param videoid
     * @param videoCreateid
     */
    public void userLikeVideos(String userid, String videoid, String videoCreateid);

    /**
     * 取消点赞
     * @param userid
     * @param videoid
     * @param videoCreateid
     */
    public void userUnLikeVideos(String userid, String videoid, String videoCreateid);

    /**
     * 用户收藏的视频
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult showMyLike(String userid, Integer page, Integer pageSize);

    /**
     * 我关注的视频
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult showMyFollowsVideos(String userid, Integer page, Integer pageSize);

    /**
     * 显示视频的用户分页评论
     * @param videoid
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult showVideoUserComment(String videoid, Integer page, Integer pageSize);
}
