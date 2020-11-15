package com.github.xabgesagtx.example.Thread;

import com.alibaba.fastjson.JSONObject;
import com.github.xabgesagtx.example.ExampleBot;
import com.github.xabgesagtx.example.utils.httpsPost;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

public class TestThread implements Callable {

    private static  final  String URL = "http://bbs.cloudsee.com/service/yst-online?cloudNum=";

    private static final Logger logger = LoggerFactory.getLogger(TestThread.class);

    public  Connection.Response response;
    @Override
    public String  call() throws Exception {
        String resutlt ="false";
        response = httpsPost.get(URL+Thread.currentThread().getName());
        if ("200".equals(String.valueOf(response.statusCode()))){
            logger.info(Thread.currentThread().getName()+"："+response.body());
            //logger.info(JSONObject.parseObject(resp.body()).getString("onln"));
            if(!StringUtils.isEmpty(JSONObject.parseObject(response.body()).getString("onln"))){
                if (JSONObject.parseObject(response.body()).getString("onln").equals("1")){
                    resutlt =  Thread.currentThread().getName();
                }
            }
        }
       return resutlt;
    }

}
