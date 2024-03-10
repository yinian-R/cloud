package com.wymm.common.excel.processor;

import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.handler.ExcelHandleHelper;
import com.wymm.common.excel.util.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractWriteProcessor implements WriteProcessor {
    
    private final ExcelConfigProperties configProperties;
    
    /**
     * 初始化 HttpServletResponse（ContentType、CharacterEncoding、Header）
     *
     * @param response      HttpServletResponse
     * @param excelResponse ExcelResponse
     */
    protected void initResponse(HttpServletResponse response, ExcelResponse excelResponse) {
        Function<String, String> fileNameWrapper = excelResponse.fileNameTimeSuffix() ? ExcelUtils::fileNameJoinDateTime : Function.identity();
        ExcelUtils.initResponse(response, excelResponse.fileName(), fileNameWrapper, excelResponse.suffix());
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
                .inMemory(excelResponse.inMemory());
        
        // 设置模板文件
        if (ObjectUtils.isNotEmpty(excelResponse.template())) {
            String templatePath = configProperties.getTemplatePath();
            String templateFilePath = ObjectUtils.isEmpty(templatePath) ?
                    getTemplateFilePath(excelResponse) : templatePath + File.separator + getTemplateFilePath(excelResponse);
            Resource resourceObj = ResourceUtil.getResourceObj(templateFilePath);
            InputStream stream = resourceObj.getStream();
            excelWriterBuilder.withTemplate(stream);
        }
        
        // 设置策略处理类
        if (ObjectUtils.isNotEmpty(ExcelHandleHelper.getWriteHandlers())) {
            for (WriteHandler writeHandler : ExcelHandleHelper.getWriteHandlers()) {
                excelWriterBuilder.registerWriteHandler(writeHandler);
            }
        }
        
        return excelWriterBuilder.build();
    }
    
    protected String getTemplateFilePath(ExcelResponse excelResponse) {
        return String.join(File.separator, excelResponse.template());
    }
    
}
