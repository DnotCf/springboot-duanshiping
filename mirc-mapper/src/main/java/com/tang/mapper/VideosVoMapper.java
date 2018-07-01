package com.tang.mapper;

import com.tang.pojo.Videos;
import com.tang.pojo.VideosVo;
import com.tang.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosVoMapper extends MyMapper<Videos> {

    public List<VideosVo> queryListVideosVO(@Param("videoDesc") String videoDesc,@Param("userId") String userId);

    public void addLikeCounts(String videoid);

    public void reduceLikeCounts(String videoid);

    /**
     * 用户收藏的视频
     * @param userid
     * @return
     */
    public List<VideosVo> queryMyLikeVideos(String userid);

    /**
     * 用户关注的人的所有视频
     * @param userid
     * @return
     */
    public List<VideosVo> querMyFollowVideos(String userid);
}