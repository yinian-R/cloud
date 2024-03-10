package com.wymm.common.excel.handler.write;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 根据 @DateTimeFormat 设置单元格日期格式
 * example: {@code
 *      @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
 *      private LocalDateTime createTime;
 * }
 */
@Slf4j
public class DateFormatStyleWriteHandler implements CellWriteHandler {
    
    @Override
    public void beforeCellCreate(CellWriteHandlerContext context) {
        beforeCellCreate(context.getWriteWorkbookHolder(), context.getWriteSheetHolder(), context.getWriteTableHolder(), context.getRow(),
                context.getHeadData(), context.getColumnIndex(), context.getRelativeRowIndex(), context.getHead());
    }
    
    protected void beforeCellCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                    Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        if (!isHead) {
            return;
        }
        DateTimeFormat dateTimeFormat = AnnotationUtils.getAnnotation(head.getField(), DateTimeFormat.class);
        if (dateTimeFormat == null || ObjectUtils.isEmpty(dateTimeFormat.value())) {
            return;
        }
        Sheet sheet = writeSheetHolder.getSheet();
        
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setFormat(dateTimeFormat.value());
        writeCellStyle.setDataFormatData(dataFormatData);
        CellStyle cellStyle = writeWorkbookHolder.createCellStyle(writeCellStyle, null);
        sheet.setDefaultColumnStyle(columnIndex, cellStyle);
    }
    
}
