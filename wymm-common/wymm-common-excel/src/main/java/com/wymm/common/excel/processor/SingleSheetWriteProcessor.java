package com.wymm.common.excel.processor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.annotation.WriteSheetParam;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.util.ExcelException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

public class SingleSheetWriteProcessor extends AbstractWriteProcessor {
    
    public SingleSheetWriteProcessor(ExcelConfigProperties configProperties) {
        super(configProperties);
    }
    
    /**
     * 是否支持
     *
     * @param executable    executable
     * @param excelResponse excelResponse
     * @return true 支持
     */
    @Override
    public boolean support(Method executable, ExcelResponse excelResponse) {
        Class<?> methodReturnType = executable.getReturnType();
        return Collection.class.isAssignableFrom(methodReturnType) && excelResponse.writeSheets().length == 1;
    }
    
    /**
     * 校验注释
     *
     * @param resultValue   resultValue
     * @param excelResponse ExcelResponse
     */
    @Override
    public void check(Object resultValue, ExcelResponse excelResponse) {
        if (ObjectUtils.isEmpty(excelResponse.fileName())) {
            throw new ExcelException("@ResponseExcel fileName 不能为空");
        }
        if (ObjectUtils.isEmpty(excelResponse.writeSheets())) {
            throw new ExcelException("@ResponseExcel writeSheets 不能为空");
        }
        if (excelResponse.writeSheets().length != 1) {
            throw new ExcelException("@ResponseExcel writeSheets expect 1. actual " + excelResponse.writeSheets().length);
        }
        WriteSheetParam sheet = excelResponse.writeSheets()[0];
        if (ObjectUtils.isEmpty(sheet.sheetName())) {
            throw new ExcelException("@ResponseExcel writeSheets.sheetName 不能为空");
        }
        if (ObjectUtils.isNotEmpty(excelResponse.template())) {
            throw new ExcelException("@ResponseExcel write sheets not support template");
        }
        
    }
    
    /**
     * 导出
     *
     * @param resultValue   resultValue
     * @param response      response
     * @param excelResponse 注解
     */
    @Override
    public void export(Object resultValue, HttpServletResponse response, ExcelResponse excelResponse) {
        this.check(resultValue, excelResponse);
        
        Collection<?> list = (Collection<?>) resultValue;
        
        this.initResponse(response, excelResponse);
        
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(response, excelResponse);
            
            WriteSheetParam sheet = excelResponse.writeSheets()[0];
            WriteSheet writeSheet = EasyExcel.writerSheet(sheet.sheetName())
                    .head(sheet.head())
                    .build();
            
            excelWriter.write(list, writeSheet);
        } finally {
            if (excelWriter != null) {
                // 关闭流
                excelWriter.finish();
            }
        }
    }
    
}
