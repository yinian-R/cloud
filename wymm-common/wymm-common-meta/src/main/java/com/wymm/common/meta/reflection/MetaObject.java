package com.wymm.common.meta.reflection;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class MetaObject {
    
    private final Object originalObject;
    
    @SneakyThrows
    public void setValue(String filedName, Object fieldValue) {
        if (ObjectUtils.isNotEmpty(originalObject) && ObjectUtils.isNotEmpty(filedName)) {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(filedName, originalObject.getClass());
            Method writeMethod = propertyDescriptor.getWriteMethod();
            writeMethod.invoke(originalObject, fieldValue);
        }
    }
    
}
