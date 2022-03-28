package com.wymm.common.excel.handler;

import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.utils.ExcelException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractSheetWriteHandler implements SheetWriteHandler, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 是否支持
     *
     * @param o Object
     * @return true 支持
     */
    @Override
    public boolean support(Object o) {
        if (o instanceof List) {
            return true;
        } else {
            throw new ExcelException("@ResponseExcel 返回值必须为List类型");
        }
    }
    
    /**
     * 校验注释
     *
     * @param excelResponse ExcelResponse
     */
    @Override
    public void check(ExcelResponse excelResponse) {
        // todo
    }
    
    /**
     * 导出
     *
     * @param o             Object
     * @param response      相应对象
     * @param excelResponse 注解
     */
    @Override
    public void export(Object o, HttpServletResponse response, ExcelResponse excelResponse) {
    
    }
}
