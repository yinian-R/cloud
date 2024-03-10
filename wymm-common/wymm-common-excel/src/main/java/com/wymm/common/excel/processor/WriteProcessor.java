package com.wymm.common.excel.processor;

import com.wymm.common.excel.annotation.ExcelResponse;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public interface WriteProcessor {
    
    /**
     * 是否支持
     *
     * @param executable    executable
     * @param excelResponse excelResponse
     * @return true 支持
     */
    boolean support(Method executable, ExcelResponse excelResponse);
    
    /**
     * 校验注释
     *
     * @param resultValue   resultValue
     * @param excelResponse ExcelResponse
     */
    void check(Object resultValue, ExcelResponse excelResponse);
    
    /**
     * 导出
     *
     * @param resultValue   resultValue
     * @param response      相应对象
     * @param excelResponse 注解
     */
    void export(Object resultValue, HttpServletResponse response, ExcelResponse excelResponse);
}
