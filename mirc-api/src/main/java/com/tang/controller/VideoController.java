package com.tang.controller;


import com.tang.pojo.Bgm;
import com.tang.pojo.Users;
import com.tang.pojo.Videos;
import com.tang.pojoVo.UsersVo;
import com.tang.service.BgmService;
import com.tang.service.UserService;
import com.tang.service.VideoService;
import com.tang.utils.MergeVideoMp3;
import com.tang.utils.PageResult;
import com.tang.utils.ResponseJSONResult;
import com.tang.utils.VideoStatusEnum;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(value = "视频相关业务的接口",tags = "视频相关业务的controller")
@RequestMapping("/video")
public class VideoController extends BaseController {


    @Autowired
    private BgmService bgmService;
    @Autowired
    private VideoService videoService;
    @GetMapping("/hello1")
    public String hello1() {
        return "hello";
    }
    @ApiOperation(value = "视频上传", notes = "视频上传的接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "userid",value = "用户id",required = true,dataType = "String",paramType = "form"),
                    @ApiImplicitParam(name ="bgmId",value="背景音乐id",required = true,dataType = "String",paramType = "form"),
                    @ApiImplicitParam(name ="videoSeconds",value="视频播放长度",required = true,dataType = "Double",paramType = "form"),
                    @ApiImplicitParam(name ="videoWidth",value="视频宽度",required = true,dataType = "String",paramType = "form"),
                    @ApiImplicitParam(name ="videoHeight",value="视频高度",required = true,dataType = "String",paramType = "form"),
                    @ApiImplicitParam(name ="videoDesc",value="视频描述",required = true,dataType = "String",paramType = "form"),
            }
    )
    @PostMapping(value = "/uploadVideo",headers = "content-type=multipart/form-data")
    public ResponseJSONResult Upload( String userid,
                                      String bgmId,
                                      double videoSeconds,
                                      int videoWidth,
                                      int videoHeight,
                                      String videoDesc,
                                       @ApiParam(value = "短视频",required = true)
                                       MultipartFile file) throws Exception {

        //文件保存的命名空间
        if (StringUtils.isBlank(userid)) {
            return ResponseJSONResult.errorMsg("上传出错");
        }

        //保存到数据库中的相对路径
        String uploadPathDB=File.separator+userid+File.separator+"video";
         String coverPathDB=File.separator+userid+File.separator+"video";
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        //文件上传的最终保存路径
        String finalFacePath=null;
        File outFile=null;
        try {
            if (file != null) {

                String filename = file.getOriginalFilename();
                String extend = FilenameUtils.getExtension(filename);
                String filename1 = UUID.randomUUID().toString();
                System.out.println(filename1+extend);
                //String filenamePerfix=filename.split("//.")[0];
                String filenamePerfix=UUID.randomUUID().toString();
                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径
                    finalFacePath = NAMESPACE + uploadPathDB +File.separator +filename1+"."+extend;
                    //设置数据库保存的路径
                    uploadPathDB = uploadPathDB + File.separator + filename1+"."+extend;
                    coverPathDB = coverPathDB + File.separator + filenamePerfix + ".jpg";
                    outFile = new File(finalFacePath);
                    if(outFile.getParentFile()!=null || !outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    file.transferTo(outFile);

//                    fileOutputStream = new FileOutputStream(outFile);
//                    inputStream = file.getInputStream();
//                    //apache的IO工具
//                    IOUtils.copy(inputStream, fileOutputStream);

                }
             }else{
                return ResponseJSONResult.errorMsg("上传出错");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        if (StringUtils.isNotBlank(bgmId)) {

            //合成视频
            String newvideoName = UUID.randomUUID().toString() + ".mp4";
            String videoInputPath = finalFacePath;
            Bgm bgm = bgmService.selectById(bgmId);
            String bgmInputPath = NAMESPACE + bgm.getPath();
            uploadPathDB=File.separator+userid+File.separator+"video";
            uploadPathDB=uploadPathDB+File.separator+newvideoName;

            String outputPath = NAMESPACE + uploadPathDB;
            MergeVideoMp3 videoMp3 = new MergeVideoMp3(FFMPEGEXE);
            videoMp3.convertor(videoInputPath, bgmInputPath, videoSeconds, outputPath);



        }

        //保存截图

        String outputPath = NAMESPACE + coverPathDB;
        MergeVideoMp3 m = new MergeVideoMp3(FFMPEGEXE);
        m.converVideoCover(finalFacePath, outputPath);

//        if (outFile != null) {
//            outFile.delete();
//        }
        //保存到数据库
        Videos video = new Videos();
        video.setUserId(userid);
        video.setAudioId(bgmId);
        video.setVideoDesc(videoDesc);
        video.setVideoPath(uploadPathDB);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoWidth(videoWidth);
        video.setVideoHeight(videoHeight);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());
        video.setCoverPath(coverPathDB);

        String videoId = videoService.save(video);
        return ResponseJSONResult.ok(videoId);
    }



    @ApiOperation(value = "视频封面上传", notes = "视频封面上传的接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "userid",value = "用户id",required = true,dataType = "String",paramType = "form"),
                    @ApiImplicitParam(name = "videoId",value = "视频id",required = true,dataType = "String",paramType = "form")
            }
    )
    @PostMapping(value = "/uploadCover",headers = "content-type=multipart/form-data")
    public ResponseJSONResult uploadCover( String userid,String videoId,
                                      @ApiParam(value = "视频封面")
                                              MultipartFile file) throws Exception {

        //文件保存的命名空间
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(videoId)) {
            return ResponseJSONResult.errorMsg("视频和用户id不能为空");
        }

        //保存到数据库中的相对路径
        String uploadPathDB=File.separator+userid+File.separator+"video";
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        //文件上传的最终保存路径
        String finalFacePath=null;
        try {
            if (file != null) {

                String filename = file.getOriginalFilename();

                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径
                    finalFacePath = NAMESPACE + uploadPathDB +File.separator +filename;
                    //设置数据库保存的路径
                    uploadPathDB = uploadPathDB + File.separator + filename;

                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile()!=null || !outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    /*file[0].transferTo(outFile);
                     */
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    //apache的IO工具
                    IOUtils.copy(inputStream, fileOutputStream);



                }
            }else{
                return ResponseJSONResult.errorMsg("上传失败");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        //保存到数据库
        videoService.updateCover(videoId, uploadPathDB);
        return ResponseJSONResult.ok();
    }

    @ApiOperation(value = "分页搜索视频记录", notes = "分页搜索视频记录的接口")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",required = true,dataType = "Integer",paramType = "query"),
           // @ApiImplicitParam(name = "pageSize",value = "每页的大小",required = true,dataType = "Integer",paramType = "query")
            @ApiImplicitParam(name="isSaveRecord",value = "是否保存,1保存,0&&''不保存热搜词")
    })
    @PostMapping("/pageList")
    public ResponseJSONResult pageList(@ApiParam(required = false) @RequestBody Videos video,Integer page, Integer isSaveRecord) {

        if (page==null) {
            page=1;
        }
        PageResult p = videoService.getAllVideos(isSaveRecord, video, page, PAGESIZE);
        return ResponseJSONResult.ok(p);
    }

    @ApiOperation(value = "我的作品视频记录", notes = "分页搜索我的作品的记录的接口")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页的大小",required = false,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="isSaveRecord",value = "是否保存,1保存,0&&''不保存热搜词")
    })
    /**
     * 作品
     */
    @PostMapping("/showAll")
    public ResponseJSONResult showAll(@ApiParam(required = false) @RequestBody Videos video,Integer page, Integer isSaveRecord,Integer pageSize) {

        if (page==null) {
            page=1;
        }
        if (pageSize == null) {
            pageSize = PAGESIZE;
        }
        PageResult p = videoService.getAllVideos(isSaveRecord, video, page, pageSize);
        return ResponseJSONResult.ok(p);
    }

    /**
     * 收藏
     * @param
     * @param page
     * @param
     * @return
     */
    @ApiOperation(value = "用户收藏的视频接口",notes = "用户收藏的视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页的大小",required = true,dataType = "Integer",paramType = "query"),

    })
    @PostMapping("/showMyLike")
    public ResponseJSONResult showMyLike( String userid,Integer page, Integer pageSize) {

        if (StringUtils.isBlank(userid)) {
             return ResponseJSONResult.ok();
        }
        if (page==null) {
            page=1;
        }
        if (pageSize==null) {
            pageSize=PAGESIZE;
        }
        PageResult p = videoService.showMyLike(userid, page,pageSize);
        return ResponseJSONResult.ok(p);
    }

    /**
     * 关注
     * @param
     * @param page
     * @param
     * @return
     */
    @ApiOperation(value = "用户关注的视频接口",notes = "用户关注的视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页的大小",required = true,dataType = "Integer",paramType = "query"),

    })
    @PostMapping("/showMyFollow")
    public ResponseJSONResult showMyFollow( String userid,Integer page, Integer pageSize) {

        if (StringUtils.isBlank(userid)) {
            return ResponseJSONResult.ok();
        }
        if (page==null) {
            page=1;
        }
        if (pageSize==null) {
            pageSize=PAGESIZE;
        }
        PageResult p = videoService.showMyFollowsVideos(userid, page,pageSize);
        return ResponseJSONResult.ok(p);
    }




    @ApiOperation(value = "热搜词的接口",notes = "热搜词的接口")
    @PostMapping("/hotWords")
    public ResponseJSONResult hotWords() {

        return ResponseJSONResult.ok(videoService.hotWords());
    }

    @ApiOperation(value = "点赞的接口",notes = "点赞的接口")
    @PostMapping("/userLike")
    public ResponseJSONResult userLikeVideo(String userid,String videoId,String videoCreateId) {
        videoService.userLikeVideos(userid, videoId, videoCreateId);
        return ResponseJSONResult.ok();
    }


    @ApiOperation(value = "取消点赞的接口",notes = "取消点赞的接口")
    @PostMapping("/userUnlike")
    public ResponseJSONResult userUnLikeVideo(String userid,String videoId,String videoCreateId) {
        videoService.userUnLikeVideos(userid, videoId, videoCreateId);
        return ResponseJSONResult.ok();
    }

    @ApiOperation(value = "获取视频评论的接口",notes = "获取视频评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoid",value = "视频id",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "page",value = "当前页数",required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页的大小",required = true,dataType = "Integer",paramType = "query"),

    })
    @PostMapping("/showComment")
    public ResponseJSONResult getALlComments(String videoid, Integer page, Integer pageSize) {

        if(StringUtils.isBlank(videoid)){
            return ResponseJSONResult.errorMsg("");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult p = videoService.showVideoUserComment(videoid, page, pageSize);
        return ResponseJSONResult.ok(p);
    }
}
