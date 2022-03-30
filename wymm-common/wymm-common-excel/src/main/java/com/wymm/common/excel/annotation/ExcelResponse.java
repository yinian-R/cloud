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
     * Excel type.The default is xlsx
     */
    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;
    
    /**
     * Write excel in memory. Default false, the cache file is created and finally written to excel.
     * Comment and RichTextString are only supported in memory mode.
     */
    boolean inMemory() default false;
    
    /**
     * Whether the encryption
     * WARRING:Encryption is when the entire file is read into memory, so it is very memory intensive.
     */
    String password() default "";
    
    /**
     * Template file
     *
     * use local file example:
     * "file:/home/demo.xlsx"
     * "/home/demo.xlsx"
     *
     * class resource file example:
     * "excel/demo.xlsx"
     */
    String template() default "";
    
    /**
     * Sheet
     */
    Sheet[] sheets() default {};
    
    @interface Sheet {
        
        /**
         * head
         */
        Class<?> head() default Void.class;
    
        /**
         * Index of sheet,0 base.
         */
        int sheetNo() default 0;
        
        /**
         * The name of sheet.
         */
        String sheetName() default "";
        
    }
    
}
