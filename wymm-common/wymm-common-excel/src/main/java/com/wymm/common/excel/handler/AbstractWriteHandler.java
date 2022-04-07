package com.wymm.common.excel.handler;

import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.util.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RequiredArgsConstructor
public abstract class AbstractWriteHandler implements WriteHandler {
    
    private final ExcelConfigProperties configProperties;
    
    protected void setExportFile(HttpServletResponse response, ExcelResponse excelResponse) {
        setExportFile(response, excelResponse.fileName(), excelResponse.suffix());
    }
    
    /**
     * 设置导出文件
     *
     * @param response response
     * @param fileName 文件名
     * @param suffix   文件后缀
     */
    @SneakyThrows(UnsupportedEncodingException.class)
    public static void setExportFile(HttpServletResponse response, String fileName, ExcelTypeEnum suffix) {
        fileName = ExcelUtils.fileNameJoinDateTime(fileName);
        fileName = String.format("%s%s", URLEncoder.encode(fileName, "UTF-8"), suffix.getValue());
        
        // 根据实际的文件类型找到对应的 contentType
        String contentType = MediaTypeFactory.getMediaType(fileName)
                .map(MediaType::toString)
                .orElse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
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
