package com.wymm.common.excel.utils;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDateTime;

public abstract class ExcelUtils {
    
    /**
     * @param fileName fileName
     * @return fileName-yyyyMMddHHmmss
     */
    public static String fileNameJoinDateTime(String fileName) {
        return fileName + "-" + LocalDateTime.now().format(DatePattern.PURE_DATETIME_FORMATTER);
    }
    
}
