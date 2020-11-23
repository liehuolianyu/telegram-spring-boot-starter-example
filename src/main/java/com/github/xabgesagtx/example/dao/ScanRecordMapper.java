package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;

public interface ScanRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScanRecord record);

    int insertSelective(ScanRecord record);

    ScanRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScanRecord record);

    int updateByPrimaryKey(ScanRecord record);

    int selectCountByUserId(Integer userId);

    int updateByUserId(ScanRecord record);
}