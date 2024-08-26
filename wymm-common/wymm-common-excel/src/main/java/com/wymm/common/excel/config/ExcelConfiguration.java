package com.wymm.common.excel.config;

import com.wymm.common.excel.aop.ExcelResponseReturnValueProcessor;
import com.wymm.common.excel.processor.*;
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
    public ExcelResponseReturnValueProcessor responseExcelReturnValueHandler(List<HttpMessageConverter<?>> converters,
                                                                             List<WriteProcessor> writeProcessorList) {
        return new ExcelResponseReturnValueProcessor(converters, writeProcessorList);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SingleSheetWriteProcessor singleSheetWriteHandler(ExcelConfigProperties excelConfigProperties) {
        return new SingleSheetWriteProcessor(excelConfigProperties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public EnhanceProcessor enhanceProcessor(ExcelConfigProperties excelConfigProperties) {
        return new EnhanceProcessor(excelConfigProperties);
    }
    
    
    @Bean
    @ConditionalOnMissingBean
    public BlankFileProcessor blankFileProcessor(ExcelConfigProperties excelConfigProperties) {
        return new BlankFileProcessor(excelConfigProperties);
    }
}
