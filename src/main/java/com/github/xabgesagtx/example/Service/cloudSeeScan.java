package com.github.xabgesagtx.example.Service;


import com.github.xabgesagtx.example.Service.impl.ScanRecordServiceImpl;
import com.github.xabgesagtx.example.entity.ScanRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cloudSeeScan {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee.class);

    @Autowired
    DealCloudSee cloudSee;

    @Autowired
    ScanRecordServiceImpl scanRecordService;

    @Value("${file.sleep}")
    private Long SLEEP_TIME;

    @Value("${file.outputpath}")
    private String FILE_PATH;


    @Async
    public void doScan(Integer startNum, Integer endNUm, String FILE_PATH, String startHead, Long SLEEP_TIME, Integer userId) {
        for (; startNum <= endNUm; startNum++) {
            try {
                cloudSee.executeForTelegram(FILE_PATH + startHead + endNUm + ".txt", startHead, startNum, endNUm, userId);
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

    public void ScanSchedual() {
        List<ScanRecord> records = scanRecordService.selectNotScan(0);
        if (records != null && records.size() > 0) {
            ScanRecord record = records.get(0);
            Integer startNum = record.getStartNum();
            Integer endNUm = record.getEndNum();
            String startHead = record.getStartHead();
            Integer userId = record.getUserId();
            for (; startNum <= endNUm; startNum++) {
                try {
                    cloudSee.executeForTelegram(FILE_PATH + startHead + endNUm + ".txt", startHead, startNum, endNUm, userId);
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    logger.info(e.getMessage());
                }

            }
        }
    }

}
