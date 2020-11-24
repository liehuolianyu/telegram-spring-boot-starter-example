package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.ChatList;

public interface ChatListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatList record);

    int insertSelective(ChatList record);

    ChatList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatList record);

    int updateByPrimaryKey(ChatList record);
}