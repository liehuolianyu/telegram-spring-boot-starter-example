package com.github.xabgesagtx.example.Service;


import com.github.xabgesagtx.example.entity.ScanRecord;
import com.github.xabgesagtx.example.entity.User;

import java.util.List;

public interface ScanRecordService {

    boolean hasRecord(Integer user_id);

    Integer insert(String startHead,Integer startNUm,Integer endNUm,Integer userId);


    Integer updateByUserId(ScanRecord record);

    List<ScanRecord> selectNotScan(Integer state);

    Integer updateScanDateById(Integer id);

    Integer updateScanResult(Integer id,Integer is_finish,Integer resultCount);

    ScanRecord selectById(Integer id);


}
