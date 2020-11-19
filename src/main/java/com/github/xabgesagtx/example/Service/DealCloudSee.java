package com.github.xabgesagtx.example.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.xabgesagtx.example.utils.FileUtils;
import com.github.xabgesagtx.example.utils.httpsPost;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class DealCloudSee {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee.class);

    private static  final  String URL = "http://bbs.cloudsee.com/service/yst-online?cloudNum=";

    @Value("${file.outputpath}")
    private  String FILE_PATH ;

    @Async
    public void execute(String path,String val){
        Connection.Response response = null;
        try {
            response = httpsPost.get(URL+val);
            if ("200".equals(String.valueOf(response.statusCode()))) {
                logger.info(Thread.currentThread().getName() + "：" + val + " :" + response.body());
                //logger.info(JSONObject.parseObject(resp.body()).getString("onln"));
                if (!StringUtils.isEmpty(response.body())) {
                    if (!StringUtils.isEmpty(JSONObject.parseObject(response.body()).getString("onln"))) {
                        if (JSONObject.parseObject(response.body()).getString("onln").equals("1")) {
                            FileUtils.FileString(path, val,true);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.info("调用接口报错："+e.getMessage());
        }
    }

}
