package com.wymm.common.meta.aspect;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class Configuration {
    
    /**
     * 需拆的类信息
     */
    private final Map<Class<?>, Consumer<?>> unboxMap = new HashMap<>();
    
    /**
     * 预处理信息
     */
    private final Map<Class<?>, MappedStatement> mappedStatementMap = new HashMap<>();
    
    public Configuration() {
        this.setUnboxMap();
    }
    
    private void setUnboxMap() {
        unboxMap.put(List.class, v -> {
            if (ObjectUtils.isNotEmpty(v)) {
            
            }
        });
    }
}
