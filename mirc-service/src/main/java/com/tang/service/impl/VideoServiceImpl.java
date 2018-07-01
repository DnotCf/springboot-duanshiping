package com.tang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tang.mapper.*;
import com.tang.pojo.*;
import com.tang.service.UserService;
import com.tang.service.VideoService;
import com.tang.utils.PageResult;
import com.tang.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private Sid sid;

    @Autowired
    private VideosVoMapper videosVoMapper;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private CommentVoMapper commentVoMapper;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String save(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);
        return id;
    }

    @Transactional(propagation =Propagation.REQUIRED)
    @Override
    public void updateCover(String videoId, String coverUrl) {

        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverUrl);

        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Videos vidoeUrl(String videoId) {
        return videosMapper.selectByPrimaryKey(videoId);
    }

    /**
     *
     * @param isSaveRecord   1：保存  0和'' 不保存
     * @param video
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PageResult getAllVideos(Integer isSaveRecord, Videos video, Integer page, Integer pageSize) {

        String desc = video.getVideoDesc();
        String userid = video.getUserId();
        if(isSaveRecord !=null && isSaveRecord==1){
            SearchRecords record = new SearchRecords();
            String id = sid.nextShort();
            record.setId(id);
            record.setContent(desc);

            searchRecordsMapper.insert(record);
        }

        //分页
        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosVoMapper.queryListVideosVO(desc,userid);
        PageInfo<VideosVo> pageInfo = new PageInfo<>(list);
        //写入到PageResult对象

        PageResult p = new PageResult();
        p.setPage(page);
        //总页数
        p.setTotal(pageInfo.getPages());
        //总记录数
        p.setReords(pageInfo.getTotal());

        p.setRows(list);

        return p;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> hotWords() {
        return searchRecordsMapper.hotWords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideos(String userid, String videoid, String videoCreateid) {
        UsersLikeVideos ulv = new UsersLikeVideos();
        String id = sid.nextShort();
        ulv.setId(id);
        ulv.setUserId(userid);
        ulv.setVideoId(videoid);
        usersLikeVideosMapper.insert(ulv);
        videosVoMapper.addLikeCounts(videoid);
        usersMapper.addLikeCounts(userid);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnLikeVideos(String userid, String videoid, String videoCreateid) {
        Example example = new Example(UsersLikeVideos.class);
         Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userid);
        criteria.andEqualTo("videoId", videoid);
        usersLikeVideosMapper.deleteByExample(example);

        videosVoMapper.reduceLikeCounts(videoid);
        usersMapper.reduceLikeCounts(userid);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult showMyLike(String userid, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosVoMapper.queryMyLikeVideos(userid);
        PageInfo<VideosVo> pglist = new PageInfo<>(list);
        PageResult p = new PageResult();
        p.setPage(page);
        //总页数
        p.setTotal(pglist.getPages());
        //总记录
        p.setReords(pglist.getTotal());
        p.setRows(list);
        return p;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult showMyFollowsVideos(String userid, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosVoMapper.querMyFollowVideos(userid);
        PageInfo<VideosVo> pageInfo = new PageInfo<>(list);
        PageResult p = new PageResult();
        p.setPage(page);
        p.setTotal(pageInfo.getPages());
        p.setReords(pageInfo.getTotal());
        p.setRows(list);
        return p;
    }

    @Override
    public PageResult showVideoUserComment(String videoid, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        List<CommentVo> list = commentVoMapper.getAllComments(videoid);

        for (CommentVo c : list) {
            c.setTimeAgo(TimeAgoUtils.format(c.getCreateTime()));

        }
        PageInfo<CommentVo> pageInfo = new PageInfo<>(list);

        PageResult p = new PageResult();
        p.setPage(page);
        p.setRows(list);
        p.setReords(pageInfo.getTotal());
        p.setTotal(pageInfo.getPages());

        return p;
    }
}
