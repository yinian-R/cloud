package com.wymm.common.excel.handler;

import com.alibaba.excel.ExcelWriter;

public interface ExcelWriterProcess {
    
    /**
     * 可进行 write 或者 fill 等操作
     *
     * @param excelWriter excelWriter
     */
    void process(ExcelWriter excelWriter);
    
}
