package com.github.xabgesagtx.example.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.xabgesagtx.example.Service.impl.ScanRecordServiceImpl;
import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.utils.FileUtils;
import com.github.xabgesagtx.example.utils.httpsPost;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class DealCloudSee {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee.class);

    private static  final  String URL = "http://bbs.cloudsee.com/service/yst-online?cloudNum=";

    @Autowired
    ScanRecordServiceImpl scanRecordService;


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

    @Async
    public void executeForTelegram(String path,String val,Integer endNUm,Integer userId){
        Connection.Response response = null;
        try {
            if (val.substring(1).equals(endNUm.toString())){
                ScanRecord scanRecord = new ScanRecord();
                scanRecord.setUserId(userId);
                scanRecord.setIsFinish(1);
                scanRecord.setScanFinishDate(new Date());
                scanRecordService.updateByUserId(scanRecord);
                logger.info("用户："+userId+" 已扫描完成， 扫描结束为："+endNUm);
            }
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
