package com.wymm.sample.excel.core;

import com.wymm.common.excel.handler.write.OptionWriteHandler;

public class DictOptionWriteHandler extends OptionWriteHandler {
    
    @Override
    protected String[] getDictOptions(String dictType) {
        return new String[]{"选项1", "选项2", dictType};
    }
    
}
