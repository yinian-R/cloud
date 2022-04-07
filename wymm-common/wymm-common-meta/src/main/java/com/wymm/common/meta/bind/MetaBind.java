package com.wymm.common.meta.bind;

import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.reflection.MetaObject;

public interface MetaBind {
    
    /**
     * 绑定元数据
     *
     * @param metaMapping 元数据注解
     * @param fieldValue  原字段值
     * @param metaObject  对象包装类
     */
    boolean bindMeta(MetaField metaMapping, Object fieldValue, MetaObject metaObject);
    
}
