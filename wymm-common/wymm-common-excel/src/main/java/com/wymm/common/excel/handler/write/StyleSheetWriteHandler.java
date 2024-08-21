package com.wymm.common.excel.handler.write;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 设置生成sheet时的默认样式
 * 推荐配合{@link StyleCellStyleStrategy}使用
 */
public class StyleSheetWriteHandler implements SheetWriteHandler {
    
    @Override
    public int order() {
        return OrderConstant.DEFAULT_DEFINE_STYLE + 1000;
    }
    
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        SheetWriteHandler.super.afterSheetCreate(writeWorkbookHolder, writeSheetHolder);
        
        Sheet sheet = writeSheetHolder.getSheet();
        PrintSetup printSetup = sheet.getPrintSetup();
        // 设置纸张大小
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        // 设置横屏
        printSetup.setLandscape(true);
        // 是否显示网格线
        sheet.setDisplayGridlines(false);
        // 打印缩放：所有列打印在一起
        sheet.setFitToPage(true);
        printSetup.setFitHeight((short) 0);
    }
}
