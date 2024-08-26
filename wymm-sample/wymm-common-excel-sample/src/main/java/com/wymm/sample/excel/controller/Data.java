package com.wymm.sample.excel.controller;

import com.alibaba.excel.util.ListUtils;
import com.wymm.sample.excel.model.DownloadData;
import com.wymm.sample.excel.model.DownloadData2;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Data {
    
    public static List<DownloadData> data() {
        List<DownloadData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setTime(LocalDateTime.now());
            list.add(data);
        }
        return list;
    }
    public static List<DownloadData2> data2() {
        List<DownloadData2> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DownloadData2 data = new DownloadData2();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setTime(LocalDateTime.now());
            list.add(data);
        }
        return list;
    }
    
}
