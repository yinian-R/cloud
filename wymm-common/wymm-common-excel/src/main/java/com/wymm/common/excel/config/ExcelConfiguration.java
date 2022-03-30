package com.wymm.common.excel.config;

import com.wymm.common.excel.aop.ExcelResponseReturnValueHandler;
import com.wymm.common.excel.handler.ExcelWriteHandler;
import com.wymm.common.excel.handler.ManySheetWriteHandler;
import com.wymm.common.excel.handler.SingleSheetWriteHandler;
import com.wymm.common.excel.handler.WriteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ExcelConfiguration {
    
    @Bean
    public ExcelConfigProperties excelConfigProperties() {
        return new ExcelConfigProperties();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ExcelResponseReturnValueHandler responseExcelReturnValueHandler(List<HttpMessageConverter<?>> converters,
                                                                           List<WriteHandler> writeHandlerList) {
        return new ExcelResponseReturnValueHandler(converters, writeHandlerList);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SingleSheetWriteHandler singleSheetWriteHandler(ExcelConfigProperties excelConfigProperties) {
        return new SingleSheetWriteHandler(excelConfigProperties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ManySheetWriteHandler manySheetWriteHandler(ExcelConfigProperties excelConfigProperties) {
        return new ManySheetWriteHandler(excelConfigProperties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ExcelWriteHandler excelWriteHandler(ExcelConfigProperties excelConfigProperties) {
        return new ExcelWriteHandler(excelConfigProperties);
    }
    
    
}
