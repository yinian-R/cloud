package com.wymm.common.meta.aspect;


import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.bind.MetaBind;
import com.wymm.common.meta.reflection.MetaObject;
import com.wymm.common.meta.util.MetaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Aspect
public class MetaAspect {
    
    private final List<MetaBind> metaBindList;
    
    @AfterReturning(value = "@annotation(com.wymm.common.meta.annotation.MetaMapping)", returning = "retVal")
    public void metaProcess(JoinPoint joinPoint, Object retVal) {
        if (retVal == null) {
            return;
        }
        Class<?> retValClass = retVal.getClass();
        // 判断是否为基础类型
        if (!MetaUtils.isPrimitiveOrWrapperOrArray(retValClass)) {
            Field[] declaredFields = retValClass.getDeclaredFields();
            for (Field field : declaredFields) {
                MetaField metaField = field.getAnnotation(MetaField.class);
                if (metaField != null) {
                    for (MetaBind metaBind : metaBindList) {
                        field.setAccessible(true);
                        Object fieldObj = ReflectionUtils.getField(field, retVal);
                        MetaObject metaObject = new MetaObject(retVal);
                        boolean bind = metaBind.bindMeta(metaField, fieldObj, metaObject);
                        if (bind) {
                            break;
                        }
                    }
                }
            }
        }
    }
    
}
