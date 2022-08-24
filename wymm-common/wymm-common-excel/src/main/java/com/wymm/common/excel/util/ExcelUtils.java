package com.wymm.common.excel.util;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.support.ExcelTypeEnum;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

public abstract class ExcelUtils {
    
    
    /**
     * 初始化 HttpServletResponse（ContentType、CharacterEncoding、Header）
     *
     * @param response response
     * @param fileName 文件名
     * @param suffix   文件后缀
     */
    @SneakyThrows(UnsupportedEncodingException.class)
    public static void initResponse(HttpServletResponse response, String fileName, ExcelTypeEnum suffix) {
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
     * @param fileName fileName
     * @return fileName-yyyyMMddHHmmss
     */
    public static String fileNameJoinDateTime(String fileName) {
        return fileName + "-" + LocalDateTime.now().format(DatePattern.PURE_DATETIME_FORMATTER);
    }
    
}
