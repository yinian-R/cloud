package com.wymm.common.excel;

import com.alibaba.excel.EasyExcel;
import com.wymm.common.excel.aop.ExcelResponseReturnValueHandler;
import com.wymm.common.excel.config.EasyExcelConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Import(EasyExcelConfiguration.class)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EasyExcel.class)
public class EasyExcelAutoConfiguration {
    
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    
    private final ExcelResponseReturnValueHandler excelResponseReturnValueHandler;
    
    @PostConstruct
    public void setResponseReturnValueHandler() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter
                .getReturnValueHandlers();
    
        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();
        newHandlers.add(excelResponseReturnValueHandler);
        assert returnValueHandlers != null;
        newHandlers.addAll(returnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);
    }
    
}
