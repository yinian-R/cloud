package com.wymm.common.excel.processor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.annotation.FillSheetParam;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.metadata.FillCompositeWrapper;
import com.wymm.common.excel.util.ExcelException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Method;

public class SingleSheetWriteFillProcessor extends AbstractWriteProcessor {
    
    public SingleSheetWriteFillProcessor(ExcelConfigProperties configProperties) {
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
        return ObjectUtils.isNotEmpty(excelResponse.template())
                && excelResponse.fillSheets().length == 1;
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
        if (ObjectUtils.isEmpty(excelResponse.fillSheets())) {
            throw new ExcelException("@ResponseExcel fillSheets 不能为空");
        }
        if (excelResponse.fillSheets().length != 1) {
            throw new ExcelException("@ResponseExcel fillSheets expect 1. actual " + excelResponse.fillSheets().length);
        }
    }
    
    @Override
    protected ExcelWriter getExcelWriter(HttpServletResponse response, ExcelResponse excelResponse) {
        ExcelWriter excelWriter = super.getExcelWriter(response, excelResponse);
        // 打开工作簿的时候，执行重新公式计算
        excelWriter.writeContext().writeWorkbookHolder().getWorkbook().setForceFormulaRecalculation(true);
        return excelWriter;
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
            WriteSheet writeSheet;
            
            // 是否按模板写入
            FillSheetParam sheet = excelResponse.fillSheets()[0];
            if (ObjectUtils.isEmpty(sheet.sheetName())) {
                writeSheet = EasyExcel.writerSheet(sheet.sheetNo())
                        .build();
            } else {
                writeSheet = EasyExcel.writerSheet(sheet.sheetName())
                        .build();
            }
            if (FillCompositeWrapper.class.isAssignableFrom(resultValue.getClass())) {
                // 多列表组合填充
                FillCompositeWrapper fillCompositeWrapper = (FillCompositeWrapper) resultValue;
                for (Object fillDatum : fillCompositeWrapper.getFillData()) {
                    excelWriter.fill(fillDatum, writeSheet);
                }
            } else {
                // 单列表填充
                excelWriter.fill(resultValue, writeSheet);
            }
        } finally {
            if (excelWriter != null) {
                // 关闭流
                excelWriter.finish();
            }
        }
    }
    
}
