package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.entity.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface UserService {

    /**
     *
     * 插入用户
     * @param message
     * @return
     */
    Integer insert(Message message);


    /**
     * 判断是否为管理员
     * @param id
     * @return
     */
    boolean isAdmin(Integer id);

    /**
     * 判断在用户表中是否已存在
     * @param id
     * @return
     */
    boolean isExists(Integer id);


    public List<User> selectAll();

}
