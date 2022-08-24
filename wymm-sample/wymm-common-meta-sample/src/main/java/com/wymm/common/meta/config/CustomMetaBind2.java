package com.wymm.common.meta.config;

import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.bind.MetaBind;
import com.wymm.common.meta.core.MetaType;
import com.wymm.common.meta.reflection.MetaObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Order(1)
@Component
public class CustomMetaBind2 implements MetaBind {
    
    private static final Map<String, String> BOOK_TYPE_MAP = new HashMap<>();
    
    static {
        BOOK_TYPE_MAP.put("1", "编程书222");
    }
    
    @Override
    public boolean bindMeta(MetaField metaField, Object o, MetaObject o1) {
        if (MetaType.BOOK_TYPE_META.equals(metaField.type())) {
            String string = ObjectUtils.getDisplayString(o);
            String text = BOOK_TYPE_MAP.getOrDefault(string, metaField.defaultValue());
            if (text != null) {
                o1.setValue(metaField.target(), text);
                return true;
            }
        }
        return false;
    }
}
