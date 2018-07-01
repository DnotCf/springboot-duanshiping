package com.tang.controller;

import com.tang.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {


    @Autowired
    public RedisOperator redisOperator;

    //user-redis-session-key
    public static final String USER_REDIS_SESSION= "user-redis-session";

    //public  static  final String NAMESPACE="D:/app/weixiApp/fileupload";
    public  static  final String NAMESPACE="/opt/mircvideoupload";


   // public static final String FFMPEGEXE = "D:\\ffmpeg\\bin\\ffmpeg.exe";
    public static final String FFMPEGEXE = "/usr/bin/ffmpeg";

    //每页的大小
    public static final Integer PAGESIZE=5;
}
