package com.tang.service.impl;

import com.tang.mapper.BgmMapper;
import com.tang.mapper.UsersMapper;
import com.tang.pojo.Bgm;
import com.tang.pojo.Users;
import com.tang.service.BgmService;
import com.tang.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> queryBgmList() {

        List<Bgm> list = bgmMapper.selectAll();
        return list;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm selectById(String id) {
        return bgmMapper.selectByPrimaryKey(id);
    }
}
