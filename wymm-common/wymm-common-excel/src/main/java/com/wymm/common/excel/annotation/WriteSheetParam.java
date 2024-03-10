package com.wymm.common.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteSheetParam {
    /**
     * head
     */
    Class<?> head();
    
    /**
     * The name of sheet.
     * 把 sheetName 作为生成 Sheet 的名称
     */
    String sheetName();
}
