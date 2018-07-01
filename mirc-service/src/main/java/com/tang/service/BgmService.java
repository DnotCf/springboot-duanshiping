package com.tang.service;

import com.tang.pojo.Bgm;
import com.tang.pojo.Users;

import java.util.List;

public interface BgmService {

    /**
     * 返回bgm列表
     * @return
     */
    public List<Bgm> queryBgmList();

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    public Bgm selectById(String id);


}
