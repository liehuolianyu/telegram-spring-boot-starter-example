package com.github.xabgesagtx.example.Service.impl;

import com.github.xabgesagtx.example.Service.UserService;
import com.github.xabgesagtx.example.dao.UserMapper;
import com.github.xabgesagtx.example.dao.UserMapper;
import com.github.xabgesagtx.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Integer insert(Message message) {
        User user = new User();
        user.setId(message.getFrom().getId());
        user.setFirstname(message.getFrom().getFirstName());
        user.setIsbot(message.getFrom().getBot());
        user.setLastname(message.getFrom().getLastName());
        user.setUsername(message.getFrom().getUserName());
        user.setIsAdmin(0);
        return userMapper.insert(user);
    }

    @Override
    public boolean isAdmin(Integer id) {
        Boolean flag = false;
        User user = userMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(user)){
            if ("1".equals(user.getIsAdmin().toString())){
                flag =true;
            }
        }
        return flag;
    }

    @Override
    public boolean isExists(Integer id) {
        Boolean flag = true;
        User user = userMapper.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(user)){
            flag = false;
        }
        return flag;
    }
}
