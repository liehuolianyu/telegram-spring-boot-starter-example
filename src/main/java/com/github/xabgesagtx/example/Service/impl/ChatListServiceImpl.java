package com.github.xabgesagtx.example.Service.impl;

import com.github.xabgesagtx.example.Service.ChatListService;
import com.github.xabgesagtx.example.dao.ChatListMapper;
import com.github.xabgesagtx.example.entity.ChatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatListServiceImpl implements ChatListService {

    @Autowired
    ChatListMapper chatListMapper;

    @Override
    public List<ChatList> selectAll() {
        return chatListMapper.selectall();
    }
}
