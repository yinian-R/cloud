package com.wymm.common.meta.aspect;

import com.wymm.common.meta.annotation.MetaField;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class MappedStatement {
    
    /**
     * 类
     */
    private Class<?> clazz;
    
    /**
     * 具有 @MetaField 注解的字段 Map
     */
    private Map<Field, MetaField> fieldMap;
    
    /**
     * 需要拆箱的字段列表
     */
    private Set<Field> unboxFields;
    
    /**
     * clazz 是否需要拆箱
     */
    private Boolean unbox;
    
    public void addField(Field field, MetaField metaField) {
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        fieldMap.put(field, metaField);
    }
    
    public void addUnboxFields(Field unboxField) {
        if (unboxFields == null) {
            unboxFields = new HashSet<>();
        }
        unboxFields.add(unboxField);
    }
    
}
