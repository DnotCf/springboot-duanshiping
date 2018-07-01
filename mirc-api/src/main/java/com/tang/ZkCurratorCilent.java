package com.tang;

import com.tang.pojo.Bgm;
import com.tang.service.BgmService;
import com.tang.utils.BGMOperatorTypeEnum;
import com.tang.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class ZkCurratorCilent {

//    @Autowired
//    private BgmService bgmService;
    @Autowired
    private ResourceConfig resourceConfig;
    private CuratorFramework client = null;
    final static Logger log = LoggerFactory.getLogger(ZkCurratorCilent.class);
//    String ZOOKEPER_SERVERURL = resourceConfig.getZookeeperServer();
//    String fileAPth = resourceConfig.getFilePath();
//    String downlodUrl = resourceConfig.getDownlodBgmUrl();

    public void init() {

        if (client != null) {
            return;
        }
        //创建重连chen  //在重试之间等待的初始时间 最大重连次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        //创建客户端
        client = CuratorFrameworkFactory.builder().connectString(resourceConfig.getZookeeperServer())
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .namespace("admin").build();

        client.start();

        try {
            addChildWatch("/bgm");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addChildWatch(String nodePath) throws Exception {

        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, nodePath, true);
            pathChildrenCache.start();
            //添加监听器
            pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    //监听是否为add
                    if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {

                        String path = event.getData().getPath();
                        String operatorType = new String(event.getData().getData());
                        Map map = JsonUtils.jsonToPojo(operatorType, Map.class);
                        String operType = (String) map.get("operType");
                        String bgmPath = (String) map.get("operUrl");

//                        System.out.println(path + operatorType + event);
//                        String[] bgmpath = path.split("/");
//                        String bgmId = bgmpath[bgmpath.length - 1];
//                        Bgm bgm = bgmService.selectById(bgmId);
//                        if (bgm == null) {
//                            return;
//                        }
//                        String bgmPath = bgm.getPath();
                        //定义保存到本地的地址
                        String filePath = resourceConfig.getFilePath()+bgmPath;
                        //定义视频下载的url地址

                        if (operType.equals(BGMOperatorTypeEnum.ADD.type)) {
                            //String[] urlPath = bgmPath.split("\\\\");
                            String[] urlPath = bgmPath.split("/");
                            String url=resourceConfig.getDownlodBgmUrl();
                            for (int i=0;i<urlPath.length;i++) {
                                if (StringUtils.isNotBlank(urlPath[i])) {

                                    url += "/" + URLEncoder.encode(urlPath[i], "UTF-8");
                                }
                            }
                            //下载bgm到springboot服务器
                            File file = new File(filePath);
                            if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
                                file.getParentFile().mkdirs();
                            }
                            URL url1 = new URL(url);
                        /*InputStream inputStream = url1.openStream();
                        FileOutputStream outputStream = new FileOutputStream(file);
                        IOUtils.copy(inputStream, outputStream);*/
                            FileUtils.copyURLToFile(url1, file);
                            client.delete().forPath(path);
                        } else if (operType.equals(BGMOperatorTypeEnum.DELETE.type)) {
                            File file = new File(filePath);
                            FileUtils.forceDelete(file);
                            client.delete().forPath(path);

                        }


                    }

                }
            });
    }
}
