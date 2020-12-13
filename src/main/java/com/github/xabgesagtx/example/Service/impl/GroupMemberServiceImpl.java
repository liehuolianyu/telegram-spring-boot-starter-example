package com.github.xabgesagtx.example.Service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xabgesagtx.example.dao.GroupMemberMapper;
import com.github.xabgesagtx.example.entity.GroupMember;
import com.github.xabgesagtx.example.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Service
public class GroupMemberServiceImpl {

    @Autowired
    GroupMemberMapper groupMemberMapper;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 插入表
     * @param message
     * @return
     */
    public Integer insert(Message message){
        GroupMember groupMember = new GroupMember();
        String id = message.getChatId().toString()+"&"+message.getFrom().getId();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        groupMember.setBlackKeyword(0);
        groupMember.setPhotoCount(0);
        groupMember.setState(0);
        groupMember.setVideoCount(0);
        redisUtils.set(id, JSON.toJSONString(groupMember));
        return groupMemberMapper.insert(groupMember);
    }

    /**
     * 判断是否存在
     * @param message
     * @return
     */
    public Boolean isExists(Message message){
        boolean flag = false;
        String id = message.getChatId().toString()+"&"+message.getFrom().getId();
        if (StringUtils.isEmpty(redisUtils.get(id))){
            GroupMember groupMember = new GroupMember();
            groupMember.setChatId(message.getChatId().toString());
            groupMember.setUserId(message.getFrom().getId());
            if (groupMemberMapper.selectCountByGroupMember(groupMember)>0){
                flag = true;
            }
        }else {
            flag = true;
        }


        return flag;
    }

    /**
     * 踢人
     * @param message
     * @return
     */
    public KickChatMember kitoutMember(Message message){
        KickChatMember kickChatMember = new KickChatMember();
        kickChatMember.setChatId(message.getChatId());
        kickChatMember.setUserId(message.getFrom().getId());
        kickChatMember.setUntilDate(20);
        return kickChatMember;
    }

    /**
     * 获取已发送的违禁词次数
     * @param message
     * @return
     */
    public Integer getBlackword(Message message){
        Integer count = 0 ;
        String id = message.getChatId().toString()+"&"+message.getFrom().getId();
        Object object = redisUtils.get(id);
        if (!ObjectUtils.isEmpty(object)){
            JSONObject group =  JSON.parseObject(object.toString());
            Integer blackKeyWordCount = Integer.valueOf(group.getString("blackKeyword"));
            count = blackKeyWordCount;
        }


/*        GroupMember groupMember = new GroupMember();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        return groupMemberMapper.selectBlackKeywordByGroupMember(groupMember);*/
        return count;
    }

    /**
     * 增加用户使用违禁词次数
     * @param message
     */
    public void blackwordAdd(Message message){
        String id = message.getChatId().toString()+"&"+message.getFrom().getId();
        Object object = redisUtils.get(id);
        if (!ObjectUtils.isEmpty(object)){
            JSONObject group =  JSON.parseObject(object.toString());
            Integer blackKeyWordCount = Integer.valueOf(group.getString("blackKeyword"))+1;
            group.remove("blackKeyword");
            group.put("blackKeyword",blackKeyWordCount);
            redisUtils.set(id,group.toJSONString());
        }


/*        GroupMember groupMember = new GroupMember();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        groupMemberMapper.updateBlackKeywordByGroupMember(groupMember);*/
    }

    /**
     * 禁用全部权限
     * @param message
     * @return
     */
    public RestrictChatMember modUserRights(Message message){
        RestrictChatMember restrictChatMember = new RestrictChatMember();
        restrictChatMember.setChatId(message.getChatId());
        restrictChatMember.setUserId(message.getFrom().getId());
        restrictChatMember.setCanSendMessages(false);
        restrictChatMember.setCanSendMediaMessages(false);
        restrictChatMember.setCanSendOtherMessages(false);
        restrictChatMember.setCanAddWebPagePreviews(false);
        return restrictChatMember;
    }

    /**
     * 发送警告信息
     * @param chatId
     * @param text
     * @return
     */
    public SendMessage sendWarning(Long chatId,String  text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public void dealNewMember(Message message){
        List<User> users = message.getNewChatMembers();
        for (User user :users){
            String id = message.getChatId().toString()+"&"+message.getFrom().getId();
            GroupMember groupMember = new GroupMember();
            groupMember.setChatId(message.getChatId().toString());
            groupMember.setUserId(user.getId());
            groupMember.setBlackKeyword(0);
            groupMember.setPhotoCount(0);
            groupMember.setState(0);
            groupMember.setVideoCount(0);
            redisUtils.set(id, JSON.toJSONString(groupMember));
            groupMemberMapper.insert(groupMember);
        }
    }

    public List<GroupMember> selectAll(){
       return groupMemberMapper.selectAll();
    }
}
