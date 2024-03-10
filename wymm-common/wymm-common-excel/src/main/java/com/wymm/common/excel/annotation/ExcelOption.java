package com.wymm.common.excel.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelOption {
    
    @AliasFor("dict")
    String value() default "";
    
    /**
     * 字典类型
     * 优先级：dict > options
     */
    @AliasFor("value")
    String dict() default "";
    
    /**
     * 可选对象
     * 例如：{@code @ExcelOption({"选项1","选项2"})}
     * 优先级：dict > options
     */
    String[] options() default {};
    
}
