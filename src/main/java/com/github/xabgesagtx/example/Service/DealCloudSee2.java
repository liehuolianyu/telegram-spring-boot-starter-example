package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class DealCloudSee2 {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee2.class);

    @Autowired
    private DealCloudSee cloudSee;

    @Value("${file.count}")
    private  Integer ALL_Count;

    @Value("${file.sleep}")
    private Long SLEEP_TIME;

    @Value("${file.outputpath}")
    private  String FILE_PATH ;

    @Async
    public void execute(String startName ) {
        ReentrantLock lock = new ReentrantLock();
        String startHead = startName.substring(0,1);
        String[] tmp = startName.split(";");
        Integer allCount = 0;
        Integer startNum = 0;
        if (tmp.length==2){
            allCount = Integer.valueOf(tmp[1]);
            startNum = Integer.valueOf(tmp[0].trim().substring(1));
        }else {
            allCount = ALL_Count;
            startNum = Integer.valueOf(startName.trim().substring(1));
        }
        Integer endNUm = startNum+allCount;
        List<String> result = new CopyOnWriteArrayList<>();


        for (; startNum <= endNUm; startNum++) {
            try {
                cloudSee.execute(result, startHead + startNum);
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                logger.info(e.getMessage());
            }
            lock.lock();
            if (result.size() >= 2) {
                logger.info("已扫描超过10个，先写入文件，最后扫描值为：" + startNum);
                FileUtils.FileWriteListforTure(FILE_PATH + startHead + endNUm + ".txt", result);
            }
            lock.unlock();

        }
        logger.info("已扫描完成，写入文件，最后扫描值为：" + startNum);
        FileUtils.FileWriteListforTure(FILE_PATH + startHead + endNUm + ".txt", result);

    }
}
