package com.wymm.common.excel.processor;

import com.alibaba.excel.ExcelWriter;

import java.util.function.Consumer;

@FunctionalInterface
public interface ExcelWriterProcess {
    
     void accept(ExcelWriter excelWriter);
     
}
