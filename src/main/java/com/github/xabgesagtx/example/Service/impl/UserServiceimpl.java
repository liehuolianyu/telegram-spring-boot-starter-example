package com.github.xabgesagtx.example.Service.impl;

import com.github.xabgesagtx.example.Service.UserService;
import com.github.xabgesagtx.example.dao.UserDao;
import com.github.xabgesagtx.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Integer insert(Message message) {
        User user = new User();
        user.setId(message.getFrom().getId());
        user.setFirstName(message.getFrom().getFirstName());
        user.setIsBot(message.getFrom().getBot());
        user.setLastName(message.getFrom().getLastName());
        user.setUserName(message.getFrom().getUserName());
        return userDao.insert(user);
    }
}
