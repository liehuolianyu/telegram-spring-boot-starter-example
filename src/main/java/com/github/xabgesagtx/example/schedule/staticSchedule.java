package com.github.xabgesagtx.example.schedule;


import com.alibaba.fastjson.JSON;
import com.github.xabgesagtx.example.ExampleBot;
import com.github.xabgesagtx.example.Service.ScanRecordService;
import com.github.xabgesagtx.example.Service.SensitiveWordService;
import com.github.xabgesagtx.example.Service.UserService;
import com.github.xabgesagtx.example.Service.cloudSeeScan;
import com.github.xabgesagtx.example.Service.impl.GroupMemberServiceImpl;
import com.github.xabgesagtx.example.entity.GroupMember;
import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;
import com.github.xabgesagtx.example.jni.Jni;
import com.github.xabgesagtx.example.utils.RedisUtil;
import com.github.xabgesagtx.example.utils.ScheduleUtils;
import com.github.xabgesagtx.example.utils.SensitiveWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

@Component
public class staticSchedule implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);

    @Autowired
    ScheduleUtils scheduleUtils;

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtils;

    @Autowired
    SensitiveWordService sensitiveWordService;

    @Autowired
    GroupMemberServiceImpl groupMemberService;

    @Autowired
    cloudSeeScan cloudSeeScan;

    @Autowired
    ScanRecordService scanRecordService;

    @Value("${file.outputpath}")
    private  String FILE_PATH ;

    @Value("${file.sleep}")
    private Long SLEEP_TIME;

    @Value("${bot.isLinux}")
    private boolean IS_LINUX;




    /**
     * 定时群中发送视频
     */
    @Scheduled(cron = "0 0 0,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?")
    public void timerSendVideo(){
        scheduleUtils.timerSendVideo();
    }


    @Scheduled(cron = "0 5 * * * ?")
    public void timerScanCloudsee(){
        cloudSeeScan.ScanSchedual();

    }

    @Scheduled(cron = "0 5 * * * ?")
    public void timerScanRecode(){


    }






    /**
     * 项目启动时向redis放数据
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //私聊用户数据
       List<User> users =  userService.selectAll();
       for (User user : users){
           redisUtils.set(user.getId().toString(), JSON.toJSONString(user));
       }

       //群聊数据
       List<GroupMember> groupMembers = groupMemberService.selectAll();
       for (GroupMember groupMember : groupMembers){
           String id = groupMember.getChatId()+"&"+groupMember.getUserId();
           redisUtils.set(id,JSON.toJSONString(groupMember));
       }

       logger.info("初始化redis数据成功");


        SensitiveWordUtil.init(new HashSet<>(sensitiveWordService.getAllSensitiveWord()));

        logger.info("敏感词初始化成功");

        if (IS_LINUX){
            Integer result =  Jni.initSdk();
            if (1==result){
                logger.info("sdk初始化成功");
            }else {
                logger.info("sdk初始化失败");
            }
        }



    }


}
