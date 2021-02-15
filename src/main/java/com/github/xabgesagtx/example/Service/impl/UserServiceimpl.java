package com.github.xabgesagtx.example.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xabgesagtx.example.Service.UserService;
import com.github.xabgesagtx.example.dao.UserMapper;
import com.github.xabgesagtx.example.entity.User;
import com.github.xabgesagtx.example.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtils;

    @Override
    public Integer insert(Message message) {
        User user = new User();
        user.setId(message.getFrom().getId());
        user.setFirstname(message.getFrom().getFirstName());
        user.setIsbot(message.getFrom().getBot());
        user.setLastname(message.getFrom().getLastName());
        user.setUsername(message.getFrom().getUserName());
        user.setIsAdmin(0);
        redisUtils.set(message.getFrom().getId().toString(), JSON.toJSONString(user));
        return userMapper.insert(user);
    }

    @Override
    public boolean isAdmin(Integer id) {
        Boolean flag = false;
        Object object = redisUtils.get(id.toString());
        JSONObject jsonObject =JSON.parseObject(object.toString());
        String isAdmin = jsonObject.getString("isAdmin");
        if (!StringUtils.isEmpty(isAdmin)){
            if ("1".equals(isAdmin)){
                flag =true;
            }
        }
        //修改直接从redis中取数据
/*        User user = userMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(user)){
            if ("1".equals(user.getIsAdmin().toString())){
                flag =true;
            }
        }*/
        return flag;
    }

    @Override
    public boolean isExists(Integer id) {
        Boolean flag = true;
        String stukey = (String) redisUtils.get(id.toString());
        if (StringUtils.isEmpty(stukey)){
            return false;
        }
/*        if (StringUtils.isEmpty(stukey)){
            User user = userMapper.selectByPrimaryKey(id);
            if (ObjectUtils.isEmpty(user)){
                flag = false;
            }
        }*/
        return flag;
    }

    @Override
    public List<User> selectAll() {
       return userMapper.selectAll();
    }
}
