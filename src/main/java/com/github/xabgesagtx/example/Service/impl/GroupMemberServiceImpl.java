package com.github.xabgesagtx.example.Service.impl;


import com.github.xabgesagtx.example.dao.GroupMemberMapper;
import com.github.xabgesagtx.example.entity.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class GroupMemberServiceImpl {

    @Autowired
    GroupMemberMapper groupMemberMapper;

    public Integer insert(Message message){
        GroupMember groupMember = new GroupMember();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        groupMember.setBlackKeyword(0);
        groupMember.setPhotoCount(0);
        groupMember.setState(0);
        groupMember.setVideoCount(0);
        return groupMemberMapper.insert(groupMember);
    }

    public Boolean isExists(Message message){
        boolean flag = false;
        GroupMember groupMember = new GroupMember();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        if (groupMemberMapper.selectCountByGroupMember(groupMember)>0){
            flag =true;
        }
        return flag;
    }

    public KickChatMember kitoutMember(Message message){
        KickChatMember kickChatMember = new KickChatMember();
        kickChatMember.setChatId(message.getChatId());
        kickChatMember.setUserId(message.getFrom().getId());
        kickChatMember.setUntilDate(20);
        return kickChatMember;
    }

    public Integer getBlackword(Message message){
        GroupMember groupMember = new GroupMember();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        return groupMemberMapper.selectBlackKeywordByGroupMember(groupMember);
    }

    public void blackwordAdd(Message message){
        GroupMember groupMember = new GroupMember();
        groupMember.setChatId(message.getChatId().toString());
        groupMember.setUserId(message.getFrom().getId());
        groupMemberMapper.updateBlackKeywordByGroupMember(groupMember);
    }
}
