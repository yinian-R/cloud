package com.wymm.common.excel.handler;

import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.util.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public abstract class AbstractWriteHandler implements WriteHandler {
    
    private final ExcelConfigProperties configProperties;
    
    /**
     * 初始化 HttpServletResponse（ContentType、CharacterEncoding、Header）
     *
     * @param response      HttpServletResponse
     * @param excelResponse ExcelResponse
     */
    protected void initResponse(HttpServletResponse response, ExcelResponse excelResponse) {
        ExcelUtils.initResponse(response, excelResponse.fileName(), excelResponse.suffix());
    }
    
    
    /**
     * 获取 ExcelWriter
     *
     * @param response      HttpServletResponse
     * @param excelResponse ExcelResponse
     * @return ExcelWriter
     */
    @SneakyThrows(IOException.class)
    protected ExcelWriter getExcelWriter(HttpServletResponse response, ExcelResponse excelResponse) {
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(response.getOutputStream())
                // 这里设置不自动关闭流，为了发生异常时封装错误返回
                .autoCloseStream(Boolean.FALSE)
                .excelType(excelResponse.suffix())
                .inMemory(excelResponse.inMemory())
                .password(excelResponse.password());
        
        // 设置模板文件
        if (ObjectUtils.isNotEmpty(excelResponse.template())) {
            String templatePath = configProperties.getTemplatePath();
            String templateFilePath = ObjectUtils.isEmpty(templatePath) ?
                    excelResponse.template() : templatePath + File.separator + excelResponse.template();
            Resource resourceObj = ResourceUtil.getResourceObj(templateFilePath);
            InputStream stream = resourceObj.getStream();
            excelWriterBuilder.withTemplate(stream);
        }
        
        return excelWriterBuilder.build();
    }
    
}
