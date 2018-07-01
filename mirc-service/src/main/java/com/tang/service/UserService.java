package com.tang.service;

import com.tang.pojo.Comment;
import com.tang.pojo.UserReport;
import com.tang.pojo.Users;

public interface UserService {

    public Boolean userNameIsExist(String username);

    public void saveUser(Users user);

    /**
     * 用户登陆验证
     */
    public Users queryUserForLogin(String username, String password);

    /**
     * 修改用户信息,上传头像
     * @param user
     */
    public void updateUserInfo(Users user);

    /**
     * 查询用户信息
     * @param userid
     * @return
     */
    public Users query(String userid);

    /**
     * 用户是否喜欢该视频
     * @param userid
     * @param videoid
     * @return
     */
    public boolean isUerLikeVideo(String userid, String videoid);

    /**
     * 保存用户和粉丝的关系
     * @param userid
     * @param fansId
     */
    public void saveUserFansRealtion(String userid, String fansId);

    /**
     * 删除用户和粉丝的关系
     * @param userid
     * @param fansId
     */
    public void deleteUserFansRealtion(String userid, String fansId);

    /**
     * 是否关注
     * @param userid
     * @param fansid
     * @return
     */
    public boolean isUserFollowFans(String userid, String fansid);

    /**
     * 用户举报视频
     * @param userReport
     */
    public void userReport(UserReport userReport);

    /**
     * 保存用户的评论
     * @param comment
     */
    public void saveComment(Comment comment,String fatherId,String toUserId);
}
