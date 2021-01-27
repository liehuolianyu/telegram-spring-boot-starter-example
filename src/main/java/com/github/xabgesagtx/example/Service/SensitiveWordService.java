package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.entity.SensitiveWord;

import java.util.List;

public interface SensitiveWordService {

    List<String> getAllSensitiveWord();

    Integer insert(SensitiveWord sensitiveWord);
}
