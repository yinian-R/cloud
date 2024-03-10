package com.wymm.common.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FillSheetParam {
    
    /**
     * Index of sheet,0 base.
     * 填充Excel时，把数据填充到 sheetNo 的 Sheet 中
     * 填充优先级：sheetName > sheetNo
     */
    int sheetNo() default 0;
    
    /**
     * The name of sheet.
     * 填充Excel时，把数据填充到相同名称 sheetName 的 Sheet 中
     * 填充优先级：sheetName > sheetNo
     */
    String sheetName() default "";
}
