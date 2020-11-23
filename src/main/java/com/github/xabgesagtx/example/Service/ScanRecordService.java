package com.github.xabgesagtx.example.Service;


import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;

public interface ScanRecordService {

    boolean hasRecord(Integer user_id);

    Integer insert(Integer startNUm,Integer endNUm,Integer userId);

    Integer updateByUserId(ScanRecord record);

}
