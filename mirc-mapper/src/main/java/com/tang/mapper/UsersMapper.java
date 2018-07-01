package com.tang.mapper;

import com.tang.pojo.Users;
import com.tang.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

    public void addLikeCounts(String userid);

    public void reduceLikeCounts(String userid);

    /**
     * 增加粉丝数量
     * @param userid
     */
    public void addFansCounts(String userid);

    /**
     * 增加关注数量
     * @param userid
     */
    public void addFolloCounts(String userid);

    /**
     * 增加视频的粉丝数量
     * @param userid
     */
    public void reduceFansCount(String userid);

    public void reduceFolloCounts(String userid);
}