package com.wymm.common.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaField {
    
    /**
     * 数据标识
     */
    String type();
    
    /**
     * 目标字段
     */
    String target();
    
    /**
     * 目标填充字段
     */
    MetaFillTypeEnum fillType() default MetaFillTypeEnum.NAME;
    
    /**
     * 目标默认值
     */
    String defaultValue() default "";
    
    
    enum MetaFillTypeEnum {
        /**
         * 字典名称
         */
        NAME,
        /**
         * 字典别名
         */
        ALIAS_NAME,
        /**
         * 字典完整名称
         */
        FULL_NAME;
    }
}
