package com.github.xabgesagtx.example.Service.impl;

import com.github.xabgesagtx.example.Service.ScanRecordService;
import com.github.xabgesagtx.example.dao.ScanRecordMapper;
import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ScanRecordServiceImpl implements ScanRecordService {

    @Autowired
    ScanRecordMapper scanRecordMapper;


    @Override
    public boolean hasRecord(Integer user_id) {
        Boolean flag = false;
        Integer count = scanRecordMapper.selectCountByUserId(user_id);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Integer insert(Integer startNUm,Integer endNUm,Integer userId) {
        ScanRecord record = new ScanRecord();
        record.setStartNum(startNUm);
        record.setEndNum(endNUm);
        record.setUserId(userId);
        record.setIsFinish(0);
        record.setScanDate(new Date());
        return scanRecordMapper.insert(record);
    }

    @Override
    public Integer updateByUserId(ScanRecord record) {
       return scanRecordMapper.updateByUserId(record);
    }

}
