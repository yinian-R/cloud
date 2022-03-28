package com.wymm.common.excel.config;

import com.wymm.common.excel.aop.ExcelResponseReturnValueHandler;
import com.wymm.common.excel.handler.SheetWriteHandler;
import com.wymm.common.excel.handler.SingleSheetWriteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class EasyExcelConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public ExcelResponseReturnValueHandler responseExcelReturnValueHandler(List<SheetWriteHandler> sheetWriteHandlerList) {
        return new ExcelResponseReturnValueHandler(sheetWriteHandlerList);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SingleSheetWriteHandler singleSheetWriteHandler() {
        return new SingleSheetWriteHandler();
    }
    
}
