package com.wymm.common.excel.metadata;

import com.alibaba.excel.write.metadata.fill.FillWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FillCompositeWrapper {
    
    private final List<Object> fillData = new ArrayList<>();
    
    /**
     * 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 {@param prefixKey}，然后多个list必须用 FillWrapper 包裹
     * example:
     * addFillData("DICT_TYPE", List<DictDTO> list);
     * excel cell format: {DICT_TYPE.name}
     */
    public void addFillData(String prefixKey, Collection<?> data) {
        fillData.add(new FillWrapper(prefixKey, data));
    }
    
    public void addFillData(Object data) {
        fillData.add(data);
    }
    
    public List<Object> getFillData() {
        return fillData;
    }
}
