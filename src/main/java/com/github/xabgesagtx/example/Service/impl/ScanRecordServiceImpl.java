package com.github.xabgesagtx.example.Service.impl;

import com.github.xabgesagtx.example.Service.ScanRecordService;
import com.github.xabgesagtx.example.dao.ScanRecordMapper;
import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


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
    public Integer insert(String startHead,Integer startNUm,Integer endNUm,Integer userId) {
        ScanRecord record = new ScanRecord();
        record.setStartNum(startNUm);
        record.setEndNum(endNUm);
        record.setUserId(userId);
        record.setIsFinish(0);
        record.setCreateDate(new Date());
        record.setStartHead(startHead);
        return scanRecordMapper.insert(record);
    }

    @Override
    public Integer updateByUserId(ScanRecord record) {
       return scanRecordMapper.updateByUserId(record);
    }

    @Override
    public List<ScanRecord> selectNotScan(Integer state) {
        return scanRecordMapper.selectNotScan(state);
    }

    @Override
    public Integer updateScanDateById(Integer id) {
        return scanRecordMapper.updateScanDateById(id);
    }

    @Override
    public Integer updateScanResult(Integer id ,Integer is_finish,Integer resultCount) {
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setId(id);
        scanRecord.setIsFinish(is_finish);
        scanRecord.setResultCount(resultCount);
        return scanRecordMapper.updateByPrimaryKeySelective(scanRecord);
    }

    @Override
    public ScanRecord selectById(Integer id) {
        return scanRecordMapper.selectByPrimaryKey(id);
    }

}
