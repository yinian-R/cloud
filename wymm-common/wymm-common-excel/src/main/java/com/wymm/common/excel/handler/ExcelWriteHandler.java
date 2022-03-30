package com.wymm.common.excel.handler;

import com.alibaba.excel.ExcelWriter;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.utils.ExcelException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Method;

public class ExcelWriteHandler extends AbstractWriteHandler {
    
    public ExcelWriteHandler(ExcelConfigProperties configProperties) {
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
        return ExcelWriterProcess.class.isAssignableFrom(executable.getReturnType());
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
        
        this.setExportFile(response, excelResponse);
        
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(response, excelResponse);
            ((ExcelWriterProcess) resultValue).process(excelWriter);
        } finally {
            if (excelWriter != null) {
                // 关闭流
                excelWriter.finish();
            }
        }
        
    }
    
    
}
