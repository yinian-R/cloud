package com.wymm.sample.excel.model;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.wymm.common.excel.annotation.ExcelOption;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 基础数据类
 **/
@Getter
@Setter
@EqualsAndHashCode
public class DownloadData2 {
    @ExcelProperty("书籍")
    private String string;
    @ExcelProperty("发布标题")
    private Date date;
    
    @DateTimeFormat(DatePattern.NORM_DATETIME_PATTERN)
    @ExcelProperty("完结时间")
    private LocalDateTime time;
    
}
