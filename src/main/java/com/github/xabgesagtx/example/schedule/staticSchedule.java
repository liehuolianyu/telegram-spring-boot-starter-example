package com.github.xabgesagtx.example.schedule;


import com.alibaba.fastjson.JSON;
import com.github.xabgesagtx.example.ExampleBot;
import com.github.xabgesagtx.example.Service.UserService;
import com.github.xabgesagtx.example.Service.impl.GroupMemberServiceImpl;
import com.github.xabgesagtx.example.dao.UserMapper;
import com.github.xabgesagtx.example.entity.GroupMember;
import com.github.xabgesagtx.example.entity.User;
import com.github.xabgesagtx.example.utils.RedisUtils;
import com.github.xabgesagtx.example.utils.ScheduleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class staticSchedule implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);

    @Autowired
    ScheduleUtils scheduleUtils;

    @Autowired
    UserService userService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    GroupMemberServiceImpl groupMemberService;


    /**
     * 定时群中发送视频
     */
    @Scheduled(cron = "30 40 * * * ?")
    public void timerSendVideo(){
        scheduleUtils.timerSendVideo();
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

    }


}
