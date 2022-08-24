package com.wymm.common.meta.bind;

import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.reflection.MetaObject;
import org.springframework.core.annotation.Order;

/**
 * 元数据绑定类
 * 具有多个元数据绑定类时，可通过 {@link Order} 控制优先级
 */
public interface MetaBind {
    
    /**
     * 绑定元数据
     *
     * @param metaMapping 元数据注解
     * @param fieldValue  原字段值
     * @param metaObject  对象包装类
     * @return true 完成绑定操作
     */
    boolean bindMeta(MetaField metaMapping, Object fieldValue, MetaObject metaObject);
    
}
