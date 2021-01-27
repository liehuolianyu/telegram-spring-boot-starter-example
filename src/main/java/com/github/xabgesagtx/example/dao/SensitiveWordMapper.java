package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.SensitiveWord;

import java.util.List;

public interface SensitiveWordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SensitiveWord record);

    int insertSelective(SensitiveWord record);

    SensitiveWord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SensitiveWord record);

    int updateByPrimaryKey(SensitiveWord record);

    List<SensitiveWord> selectAll();
}