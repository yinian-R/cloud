package com.wymm.common.excel.processor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.annotation.WriteSheetParam;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.handler.write.StyleCellStyleStrategy;
import com.wymm.common.excel.util.ExcelException;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;

public class BlankFileProcessor extends AbstractWriteProcessor {
    
    public BlankFileProcessor(ExcelConfigProperties configProperties) {
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
        return Void.TYPE.equals(methodReturnType);
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
    }
    
    @Override
    protected void setDefaultStyle(ExcelResponse excelResponse, ExcelWriterBuilder excelWriterBuilder) {
        excelWriterBuilder.registerWriteHandler(new StyleCellStyleStrategy(true, true));
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
        
        this.initResponse(response, excelResponse);
        
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(response, excelResponse);
            
            if (ObjectUtils.isNotEmpty(excelResponse.writeSheets())) {
                for (WriteSheetParam sheet : excelResponse.writeSheets()) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheet.sheetName())
                            .head(sheet.head())
                            .build();
                    excelWriter.write(Collections.emptyList(), writeSheet);
                }
            }
        } finally {
            if (excelWriter != null) {
                // 关闭流
                excelWriter.finish();
            }
        }
    }
    
}
