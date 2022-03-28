package com.wymm.common.excel.handler;

import com.wymm.common.excel.annotation.ExcelResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SingleSheetWriteHandler extends AbstractSheetWriteHandler{
    
    /**
     * 导出
     *
     * @param o             Object
     * @param response      相应对象
     * @param excelResponse 注解
     */
    @Override
    public void export(Object o, HttpServletResponse response, ExcelResponse excelResponse) {
        List<?> list = (List<?>) o;
        System.out.println("~~~~~~~~~~~~~~66");
    }
}
