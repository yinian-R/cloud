package com.wymm.common.excel.handler;

import com.wymm.common.excel.annotation.ExcelResponse;

import javax.servlet.http.HttpServletResponse;

public interface SheetWriteHandler {
    
    /**
     * 是否支持
     *
     * @param o Object
     * @return true 支持
     */
    boolean support(Object o);
    
    /**
     * 校验注释
     *
     * @param excelResponse ExcelResponse
     */
    void check(ExcelResponse excelResponse);
    
    /**
     * 导出
     *
     * @param o             Object
     * @param response      相应对象
     * @param excelResponse 注解
     */
    void export(Object o, HttpServletResponse response, ExcelResponse excelResponse);
}
