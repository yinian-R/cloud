package com.wymm.common.excel.handler.write;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;

/**
 * 表头 * 号设置为红色
 *
 * example: {@code
 *      @ExcelProperty("标题*")
 *      private String title;
 * }
 */
@Slf4j
public class TitleStatStyleWriteHandler extends AbstractCellStyleStrategy {
    
    public static final String STAT = "*";
    
    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        Cell cell = context.getCell();
        RichTextString richStringCellValue = cell.getRichStringCellValue();
        String title = richStringCellValue.getString();
        
        if (title.endsWith(STAT)) {
            Font font = context.getWriteWorkbookHolder().getWorkbook().createFont();
            font.setColor(Font.COLOR_RED);
            int startIndex = title.length() - 1;
            int endIndex = title.length();
            richStringCellValue.applyFont(startIndex, endIndex, font);
        }
    }
    
    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
    
    }
}
