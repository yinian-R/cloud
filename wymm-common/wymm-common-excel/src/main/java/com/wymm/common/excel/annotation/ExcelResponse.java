package com.wymm.common.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

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
     * 文件名称
     *
     * @return 文件名称
     */
    String name() default "";
    
    /**
     * 文件类型
     *
     * @return 文件类型
     */
    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;
    
    /**
     * 文件密码
     *
     * @return 文件密码
     */
    String password() default "";
    
    /**
     * 内存操作
     *
     * @return true 在内存中、false 不在内存中
     */
    boolean inMemory() default true;
    
    /**
     * excel 模板
     *
     * @return String
     */
    String template() default "";
    
    /**
     * 拦截器，自定义样式等处理器
     *
     * @return WriteHandler[]
     */
    Class<? extends WriteHandler>[] writeHandler() default {};
    
    /**
     * Sheet 名称
     *
     * @return string
     */
    Sheet sheet() default @Sheet(sheetName = "sheet1");
}
