package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.ChatList;

import java.util.List;

public interface ChatListMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatList record);

    int insertSelective(ChatList record);

    ChatList selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatList record);

    int updateByPrimaryKey(ChatList record);

    List<ChatList> selectall();
}