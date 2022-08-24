package com.wymm.common.meta.aspect;


import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.bind.MetaBind;
import com.wymm.common.meta.reflection.MetaObject;
import com.wymm.common.meta.util.MetaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Aspect
public class MetaAspect {
    
    private final List<MetaBind> metaBindList;
    private final Configuration configuration;
    
    public MetaAspect(List<MetaBind> metaBindList) {
        this.metaBindList = metaBindList;
        this.configuration = new Configuration();
    }
    
    @AfterReturning(value = "@annotation(com.wymm.common.meta.annotation.MetaMapping)", returning = "retVal")
    public void metaProcess(JoinPoint joinPoint, Object retVal) {
        if (ObjectUtils.isEmpty(retVal) || ObjectUtils.isEmpty(metaBindList)) {
            return;
        }
        Class<?> retValClass = retVal.getClass();
        // 判断是否为基础类型
        if (!MetaUtils.isPrimitiveOrWrapperOrArray(retValClass)) {
            // 获取预处理数据
            MappedStatement mappedStatement = parseMappedStatement(retValClass);
            for (Field field : mappedStatement.getFieldMap().keySet()) {
                MetaField metaField = mappedStatement.getFieldMap().get(field);
                field.setAccessible(true);
                Object fieldObj = ReflectionUtils.getField(field, retVal);
                for (MetaBind metaBind : metaBindList) {
                    MetaObject metaObject = new MetaObject(retVal);
                    // 绑定元数据
                    boolean bind = metaBind.bindMeta(metaField, fieldObj, metaObject);
                    if (bind) {
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * 获取预处理信息
     *
     * @param retValClass 待处理类
     * @return 预处理信息
     */
    private MappedStatement parseMappedStatement(Class<?> retValClass) {
        return configuration.getMappedStatementMap().computeIfAbsent(retValClass, clazz -> {
            MappedStatement statement = new MappedStatement();
            statement.setClazz(clazz);
            statement.setUnbox(configuration.getUnboxMap().containsKey(clazz));
            Field[] declaredFields = retValClass.getDeclaredFields();
            for (Field field : declaredFields) {
                // 判断是否有 MetaField 注释
                if (field.isAnnotationPresent(MetaField.class)) {
                    MetaField metaField = field.getAnnotation(MetaField.class);
                    statement.addField(field, metaField);
                }
                // 判断是否需要拆箱
                if (configuration.getUnboxMap().containsKey(field.getClass())) {
                    statement.addUnboxFields(field);
                }
            }
            return statement;
        });
    }
    
}
