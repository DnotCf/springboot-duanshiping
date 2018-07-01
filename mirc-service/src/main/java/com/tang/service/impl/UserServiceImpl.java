package com.tang.service.impl;

import com.tang.mapper.*;
import com.tang.pojo.*;
import com.tang.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UserReportMapper userReportMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Boolean userNameIsExist(String username) {
        Users user =new Users();
        user.setUsername(username);
        Users res = usersMapper.selectOne(user);

        return res == null ? false: true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {

        String id = sid.nextShort();
        user.setId(id);
        usersMapper.insert(user);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        Users user = usersMapper.selectOneByExample(example);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, example);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users query(String userid) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", userid);
        Users user = usersMapper.selectOneByExample(example);
        return user;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUerLikeVideo(String userid, String videoid) {
        if(userid ==null || userid== ""){
            return false;
        }
        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("userId", userid);
        c.andEqualTo("videoId", videoid);
        List<UsersLikeVideos> like = usersLikeVideosMapper.selectByExample(example);
        if (like != null && like.size() > 0) {
            return true;
        }
        return false;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFansRealtion(String userid, String fansId) {
        UsersFans record = new UsersFans();
        String id = sid.nextShort();
        record.setId(id);
        record.setUserId(userid);
        record.setFanId(fansId);
        usersFansMapper.insert(record);

        usersMapper.addFansCounts(userid);
        usersMapper.addFolloCounts(fansId);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFansRealtion(String userid, String fansId) {

        Example example = new Example(UsersFans.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("userId", userid);
        c.andEqualTo("fanId", fansId);
        usersFansMapper.deleteByExample(example);

        usersMapper.reduceFansCount(userid);
        usersMapper.reduceFolloCounts(fansId);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserFollowFans(String userid, String fansid) {

    Example example = new Example(UsersFans.class);
    Example.Criteria c = example.createCriteria();
        c.andEqualTo("userId", userid);
        c.andEqualTo("fanId", fansid);
    List<UsersFans> like = usersFansMapper.selectByExample(example);
        if (like != null && like.size() > 0) {
        return true;
    }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userReport(UserReport userReport) {
        String id = sid.nextShort();
        userReport.setId(id);
        userReport.setCreateDate(new Date());
        userReportMapper.insert(userReport);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comment comment,String fatherId,String toUserId) {
        String id = sid.nextShort();
        comment.setId(id);
        if(StringUtils.isNotBlank(fatherId)){
            comment.setFatherCommentId(fatherId);
        }
        if(StringUtils.isNotBlank(toUserId)){
            comment.setToUserId(toUserId);
        }

        comment.setCreateTime(new Date());
        commentMapper.insert(comment);

    }
}
