package com.github.xabgesagtx.example.Service;


import com.github.xabgesagtx.example.Service.impl.ScanRecordServiceImpl;
import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class cloudSeeScan {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee.class);

    @Autowired
    DealCloudSee cloudSee;

    @Autowired
    ScanRecordServiceImpl scanRecordService;

    @Async
    public void doScan(Integer startNum,Integer endNUm,String FILE_PATH,String startHead,Long SLEEP_TIME,Integer userId)
    {
        for (; startNum <= endNUm; startNum++) {
            try {
                cloudSee.executeForTelegram(FILE_PATH + startHead + endNUm + ".txt", startHead + startNum,endNUm,userId);
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                logger.info(e.getMessage());
            }
            //老方法放在list中，导致丢失部分数据
/*            lock.lock();
            if (result.size() >= 2) {
                logger.info("已扫描超过10个，先写入文件，最后扫描值为：" + startNum);
                FileUtils.FileWriteListforTure(, result);
            }
            lock.unlock();*/

        }

    }
}
