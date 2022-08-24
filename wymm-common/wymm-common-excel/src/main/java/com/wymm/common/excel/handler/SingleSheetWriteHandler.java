package com.wymm.common.excel.handler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.util.ExcelException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

public class SingleSheetWriteHandler extends AbstractWriteHandler {
    
    public SingleSheetWriteHandler(ExcelConfigProperties configProperties) {
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
        return Collection.class.isAssignableFrom(methodReturnType);
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
        if (ObjectUtils.isEmpty(excelResponse.sheets())) {
            throw new ExcelException("@ResponseExcel sheets 不能为空");
        }
        if (ObjectUtils.isEmpty(excelResponse.sheets().length != 1)) {
            throw new ExcelException("@ResponseExcel sheets expect 1. actual " + excelResponse.sheets().length);
        }
        for (ExcelResponse.Sheet sheet : excelResponse.sheets()) {
            if (ObjectUtils.isEmpty(sheet.sheetName())) {
                throw new ExcelException("@ResponseExcel sheets.sheetName 不能为空");
            }
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
            WriteSheet writeSheet;
            
            // 是否按模板写入
            if (ObjectUtils.isNotEmpty(excelResponse.template())) {
                writeSheet = EasyExcel.writerSheet(0)
                        .build();
            } else {
                ExcelResponse.Sheet sheet = excelResponse.sheets()[0];
                writeSheet = EasyExcel.writerSheet(sheet.sheetNo(), sheet.sheetName())
                        .head(sheet.head())
                        .build();
            }
            excelWriter.write(list, writeSheet);
        } finally {
            if (excelWriter != null) {
                // 关闭流
                excelWriter.finish();
            }
        }
    }
    
}
