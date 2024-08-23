package com.wymm.common.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResponse {
    
    /**
     * Excel filename prefix
     */
    String fileName();
    
    /**
     * Excel filename add time suffix
     * true example：filename-yyyyMMddHHmmss
     * false example：filename
     */
    boolean fileNameTimeSuffix() default true;
    
    /**
     * Excel type.The default is xlsx
     */
    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;
    
    /**
     * Write excel in memory. Default false, the cache file is created and finally written to excel.
     * Comment and RichTextString are only supported in memory mode.
     */
    boolean inMemory() default false;
    
    /**
     * Template file
     * use: String.join(File.separator, template())
     * <p>
     * example: {"file:", "home", "demo.xlsx"}
     * <p>
     * use local file example:
     * "file:/home/demo.xlsx"
     * "/home/demo.xlsx"
     * <p>
     * class resource file example:
     * "excel/demo.xlsx"
     */
    String[] template() default {};
    
    /**
     * Write Sheet
     */
    WriteSheetParam[] writeSheets() default {};
    
}
