package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.entity.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {
    Integer insert(Message message);

}
