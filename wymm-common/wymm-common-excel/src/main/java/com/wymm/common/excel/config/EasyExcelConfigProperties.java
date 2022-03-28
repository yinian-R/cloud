package com.wymm.common.excel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = EasyExcelConfigProperties.PREFIX)
public class EasyExcelConfigProperties {
    
    static final String PREFIX = "excel";
    
    /**
     * 模板路径
     */
    private String templatePath = "excel";
    
}
