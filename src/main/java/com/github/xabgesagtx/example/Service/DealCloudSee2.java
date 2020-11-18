package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class DealCloudSee2 {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee2.class);

    @Autowired
    private DealCloudSee cloudSee;

    @Async
    public void execute(List result, String startHead, Integer startNum, Integer endNUm,Long sleep_time,String cloudPath){
        ReentrantLock lock = new ReentrantLock();
        for (;startNum<=endNUm;startNum++){
            try {
                cloudSee.execute(result,startHead+startNum);
                Thread.sleep(sleep_time);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                logger.info(e.getMessage());
            }
            lock.lock();
            if (result.size()>=10){
                logger.info("已扫描超过10个，先写入文件，最后扫描值为："+startNum);
                FileUtils.FileWriteListforTure(cloudPath+startHead+endNUm+".txt",result);
            }
            lock.unlock();
				/*	resp = httpsPost.get(url+startHead+startNum);
					if ("200".equals(String.valueOf(resp.statusCode()))){
						logger.info(startHead+startNum+"："+resp.body());
						//logger.info(JSONObject.parseObject(resp.body()).getString("onln"));
						if(!StringUtils.isEmpty(JSONObject.parseObject(resp.body()).getString("onln"))){
						if (JSONObject.parseObject(resp.body()).getString("onln").equals("1")){
							result.add(startHead+startNum);
						}
						}
					}*/
					/*if (result.size()>10){
						logger.info("已扫描超过10个，先写入文件，最后扫描值为："+startNum);
						fileUtils.FileWriteListforTure(FILE_PATH+startHead+endNUm+".txt",result);
						result.clear();
					}*/
        }
        logger.info("已扫描完成，写入文件，最后扫描值为："+startNum);
        FileUtils.FileWriteListforTure(cloudPath+startHead+endNUm+".txt",result);
    }

}
