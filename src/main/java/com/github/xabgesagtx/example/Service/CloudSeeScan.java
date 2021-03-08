package com.github.xabgesagtx.example.Service;


import com.github.xabgesagtx.example.Service.impl.ScanRecordServiceImpl;
import com.github.xabgesagtx.example.entity.ScanRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.xabgesagtx.example.utils.httpsPost;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class CloudSeeScan {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee.class);

    @Autowired
    DealCloudSee cloudSee;

    @Autowired
    ScanRecordService scanRecordService;

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
        List<ScanRecord> recordsOn = scanRecordService.selectNotScan(1);
        if (recordsOn != null && recordsOn.size()>0) {
            logger.info("有未完成的扫描，本次不扫描");
        }else {
            List<ScanRecord> records = scanRecordService.selectNotScan(0);
            if (records != null && records.size() > 0) {
                ScanRecord record = records.get(0);
                Integer startNum = record.getStartNum();
                Integer endNUm = record.getEndNum();
                String startHead = record.getStartHead();
                Integer id = record.getId();

                try {
                    httpsPost.get("http://test.yangge666.top:8083/cloudsee/scanNew?name=" + startNum + "&head=" + startHead + "&count=" + (endNUm - startNum) + "&id=" + id);
                    scanRecordService.updateScanResult(record.getId(),1);
                    logger.info("请求地址:http://test.yangge666.top:8083/cloudsee/scanNew?name=" + startNum + "&head=" + startHead + "&count=" + (endNUm - startNum) + "&id=" + id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("开始扫描："+startHead+startNum);
                //修改调用第三方接口，降低telegram损耗
            /*for (; startNum <= endNUm; startNum++) {
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

            }*/

            }
        }

    }

    public void dealReturn(HttpServletRequest request){
        String id = request.getParameter("id");
        scanRecordService.updateScanResult(Integer.valueOf(id),2);

    }
}
