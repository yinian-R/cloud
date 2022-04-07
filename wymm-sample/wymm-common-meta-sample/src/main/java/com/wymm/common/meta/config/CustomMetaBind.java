package com.wymm.common.meta.config;

import com.wymm.common.meta.annotation.MetaField;
import com.wymm.common.meta.bind.MetaBind;
import com.wymm.common.meta.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomMetaBind implements MetaBind {
    
    private static final Map<String, String> map = new HashMap<>();
    
    static {
        map.put("1", "编程书");
    }
    
    @Override
    public boolean bindMeta(MetaField metaField, Object o, MetaObject o1) {
        String string = ObjectUtils.getDisplayString(o);
        String text = map.getOrDefault(string, metaField.defaultValue());
        
        if (text != null) {
            System.out.println(text);
            o1.setValue(metaField.target(), text);
            return true;
        }
        return false;
    }
}
