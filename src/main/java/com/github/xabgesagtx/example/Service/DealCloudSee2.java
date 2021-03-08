package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.Service.impl.ScanRecordServiceImpl;
import com.github.xabgesagtx.example.utils.OutputLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class DealCloudSee2 {

    private static final Logger logger = LoggerFactory.getLogger(DealCloudSee2.class);

    @Autowired
    private DealCloudSee cloudSee;

    @Autowired
    ScanRecordServiceImpl scanRecordService ;

    @Autowired
    CloudSeeScan cloudSeeScan;

    @Value("${file.count}")
    private  Integer ALL_Count;

    @Value("${file.sleep}")
    private Long SLEEP_TIME;

    @Value("${file.outputpath}")
    private  String FILE_PATH ;

    @Async
    public void execute(String startHead, String startName,String count ) {
        ReentrantLock lock = new ReentrantLock();
        Integer allCount = 0;
        Integer startNum = Integer.valueOf(startName);
        startNum = Integer.valueOf(startName);
        if (!StringUtils.isEmpty(count)){
            allCount = Integer.valueOf(count);

        }else {
            allCount = ALL_Count;
        }
        Integer endNUm = startNum+allCount;

/*        List<String> result = new CopyOnWriteArrayList<>();*/


        for (; startNum <= endNUm; startNum++) {
            try {
                cloudSee.execute(FILE_PATH + startHead + endNUm + ".txt", startHead + startNum);
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(20);
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
        logger.info("已扫描完成，写入文件，最后扫描值为：" + startNum);
       /* FileUtils.FileWriteListforTure(FILE_PATH + startHead + endNUm + ".txt", result);*/

    }


    @Async
    public void scanNew(String startHead, String startName,String count ){
        ReentrantLock lock = new ReentrantLock();
        Integer allCount = 0;
        Integer startNum = Integer.valueOf(startName);
        if (!StringUtils.isEmpty(count)){
            allCount = Integer.valueOf(count);

        }else {
            allCount = ALL_Count;
        }
        Integer endNUm = startNum+allCount;

        /*        List<String> result = new CopyOnWriteArrayList<>();*/


        for (; startNum <= endNUm; startNum++) {
            try {
                cloudSee.scanNew(FILE_PATH + startHead + endNUm + ".txt", startHead ,startNum);
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(20);
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
        logger.info("已扫描完成，写入文件，最后扫描值为：" + startNum);
        /* FileUtils.FileWriteListforTure(FILE_PATH + startHead + endNUm + ".txt", result);*/
    }


    public SendMessage dealMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        if (scanRecordService.hasRecord(message.getFrom().getId())) {
            sendMessage.setChatId(message.getChatId()).setText(OutputLine.line4);
        } else {
            String startName = message.getText().substring(5);
           String[] str =  startName.trim().split("&");
           if (str == null ){
               sendMessage.setText(OutputLine.line6);
           }else if (str.length == 2){


            String startHead = str[0];
            String[] tmp = startName.split(";");
            Integer allCount = 0;
            Integer startNum = 0;

                allCount = ALL_Count;
                startNum = Integer.valueOf(str[1].trim());

            Integer endNUm = startNum + allCount;
            //改为定时任务调用
            /*cloudSeeScan.doScan(startNum,endNUm,FILE_PATH,startHead,SLEEP_TIME,message.getFrom().getId());*/
            scanRecordService.insert(startHead,startNum,endNUm,message.getFrom().getId());
            sendMessage.setText(OutputLine.line5);
           }else if (str.length == 3){
               String startHead = str[0];
               Integer allCount = 0;
               Integer startNum = 0;

                   allCount = Integer.valueOf(str[2]);
                   if (allCount>1000000){
                       sendMessage.setText(OutputLine.line8);
                   }else {
                       startNum = Integer.valueOf(str[1].trim());


                       Integer endNUm = startNum + allCount;
                       //改为定时任务调用
                     /*  cloudSeeScan.doScan(startNum, endNUm, FILE_PATH, startHead, SLEEP_TIME, message.getFrom().getId());*/
                       scanRecordService.insert(startHead,startNum, endNUm, message.getFrom().getId());
                       sendMessage.setText(OutputLine.line5);
                   }
           }
           else
               sendMessage.setText(OutputLine.line7);
        }
        return sendMessage;
    }
}
