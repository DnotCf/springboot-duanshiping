package com.tang.mapper;

import com.tang.pojo.Comment;
import com.tang.pojo.CommentVo;
import com.tang.utils.MyMapper;

import java.util.List;

public interface CommentVoMapper extends MyMapper<Comment> {

    /**
     * 获取视频的用户评论
     * @return
     */
    public List<CommentVo> getAllComments(String videoId);
}