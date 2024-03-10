package com.wymm.common.excel.handler.write;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.wymm.common.excel.annotation.ExcelOption;
import com.wymm.common.excel.util.ExcelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;

/**
 * 根据 @ExcelOption 设置单元格下拉框
 */
@Slf4j
public class OptionWriteHandler implements CellWriteHandler {
    
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
        ExcelOption option = AnnotationUtils.getAnnotation(head.getField(), ExcelOption.class);
        if (option == null) {
            return;
        }
        
        String[] options = getDictOptions(option);
        if (ObjectUtils.isEmpty(options)){
            return;
        }
        // 单元格范围地址
        int col = columnIndex;
        int firstRow = head.getHeadNameList().size();
        int lastRow = getExcelMaxRow(writeWorkbookHolder.getExcelType()) - 1;
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, col, col);
        // 设置数据验证
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(options);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        sheet.addValidationData(dataValidation);
    }
    
    protected String[] getDictOptions(ExcelOption option) {
        String dict = option.dict();
        String[] options = option.options();
        if (ObjectUtils.isNotEmpty(dict)) {
            return getDictOptions(dict);
        }
        if (ObjectUtils.isNotEmpty(options)) {
            return options;
        }
        log.error("ExcelOption is empty " + option);
        return null;
    }
    
    /**
     * 从字典中获取选项
     * 若需使用 dictType 请继承该类并实现此方法
     *
     * @param dictType 字典类型
     * @return 选项数组
     */
    protected String[] getDictOptions(String dictType) {
        throw new ExcelException("not support");
    }
    
    protected int getExcelMaxRow(ExcelTypeEnum excelType) {
        if (ExcelTypeEnum.XLSX.equals(excelType)) {
            return SpreadsheetVersion.EXCEL2007.getMaxRows();
        } else if (ExcelTypeEnum.XLS.equals(excelType)) {
            return SpreadsheetVersion.EXCEL97.getMaxRows();
        } else {
            throw new ExcelException("not support");
        }
    }
}
