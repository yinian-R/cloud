package com.wymm.common.excel.handler;

import com.alibaba.excel.write.handler.WriteHandler;

import java.util.ArrayList;
import java.util.List;

public class ExcelHandleHelper {
    
    protected static final ThreadLocal<List<WriteHandler>> LOCAL_WRITE_HANDLER = new ThreadLocal<>();
    
    public static void registerWriteHandler(WriteHandler... writeHandler) {
        for (WriteHandler handler : writeHandler) {
            getWriteHandlers().add(handler);
        }
    }
    
    public static List<WriteHandler> getWriteHandlers() {
        if (LOCAL_WRITE_HANDLER.get() == null) {
            List<WriteHandler> list = new ArrayList<>();
            LOCAL_WRITE_HANDLER.set(list);
        }
        return LOCAL_WRITE_HANDLER.get();
    }
    
    public static void clearWriteHandlers() {
        LOCAL_WRITE_HANDLER.remove();
    }
    
}
