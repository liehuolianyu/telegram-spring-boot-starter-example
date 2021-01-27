package com.github.xabgesagtx.example.Service.impl;

import com.github.xabgesagtx.example.Service.SensitiveWordService;
import com.github.xabgesagtx.example.dao.SensitiveWordMapper;
import com.github.xabgesagtx.example.entity.SensitiveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    @Override
    public List<String> getAllSensitiveWord() {
        List<String> result = new ArrayList<>();
        List<SensitiveWord> sensitiveWords = sensitiveWordMapper.selectAll();
        for (SensitiveWord sensitiveWord : sensitiveWords){
            result.add(sensitiveWord.getName());
        }
        return result;
    }

    @Override
    public Integer insert(SensitiveWord sensitiveWord) {
      return   sensitiveWordMapper.insert(sensitiveWord);
    }
}
