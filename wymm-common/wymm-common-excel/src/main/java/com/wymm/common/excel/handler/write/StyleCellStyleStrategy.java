package com.wymm.common.excel.handler.write;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;

import java.util.Collections;

/**
 * 设置生成cell时的默认样式
 */
public class StyleCellStyleStrategy extends HorizontalCellStyleStrategy implements SheetWriteHandler {
    
    @Getter
    @Setter
    private Integer order;
    
    @Override
    public int order() {
        return order == null ? OrderConstant.DEFAULT_DEFINE_STYLE + 1000 : order;
    }
    
    public StyleCellStyleStrategy() {
        this(true, true);
    }
    
    public StyleCellStyleStrategy(boolean headCellStyle, boolean contentCellStyle) {
        if (headCellStyle) {
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setWrapped(true);
            headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            headWriteCellStyle.setLocked(true);
            headWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
            headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
            headWriteCellStyle.setBorderTop(BorderStyle.THIN);
            headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            headWriteCellStyle.setBorderRight(BorderStyle.THIN);
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontName("黑体");
            headWriteFont.setFontHeightInPoints((short) 12);
            headWriteFont.setBold(false);
            headWriteCellStyle.setWriteFont(headWriteFont);
            
            setHeadWriteCellStyle(headWriteCellStyle);
        }
        
        if (contentCellStyle) {
            WriteCellStyle cellWriteCellStyle = new WriteCellStyle();
            cellWriteCellStyle.setWrapped(true);
            cellWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            cellWriteCellStyle.setLocked(true);
            cellWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
            cellWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
            cellWriteCellStyle.setBorderTop(BorderStyle.THIN);
            cellWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            cellWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            cellWriteCellStyle.setBorderRight(BorderStyle.THIN);
            WriteFont cellWriteFont = new WriteFont();
            cellWriteFont.setFontName("仿宋");
            cellWriteFont.setFontHeightInPoints((short) 11);
            cellWriteFont.setBold(false);
            cellWriteCellStyle.setWriteFont(cellWriteFont);
            setContentWriteCellStyleList(Collections.singletonList(cellWriteCellStyle));
        }
    }
}