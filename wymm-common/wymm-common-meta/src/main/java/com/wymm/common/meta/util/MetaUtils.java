package com.wymm.common.meta.util;

import org.springframework.util.ClassUtils;

public abstract class MetaUtils {
    
    /**
     * 判断是否为基础类型/基础类型包装类/基础类型数组/基础类型包装类数组
     *
     * @param clazz 要检查的类
     * @return true 是
     */
    public static boolean isPrimitiveOrWrapperOrArray(Class<?> clazz) {
        return ClassUtils.isPrimitiveOrWrapper(clazz)
                || ClassUtils.isPrimitiveArray(clazz)
                || ClassUtils.isPrimitiveWrapperArray(clazz);
    }
}
