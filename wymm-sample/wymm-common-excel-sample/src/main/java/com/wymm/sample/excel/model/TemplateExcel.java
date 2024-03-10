package com.wymm.sample.excel.model;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.wymm.common.excel.annotation.ExcelOption;
import lombok.Data;

import java.time.LocalDateTime;

@HeadStyle(fillPatternType = FillPatternTypeEnum.NO_FILL,
        borderLeft = BorderStyleEnum.NONE, borderRight = BorderStyleEnum.NONE, borderTop = BorderStyleEnum.NONE, borderBottom = BorderStyleEnum.NONE)
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle
@Data
public class TemplateExcel {
    
    @ExcelProperty("标题*")
    private String title;
    
    @ExcelProperty()
    private String author;
    @ExcelProperty("")
    private String author1;
    
    @ExcelOption(options = {"自然", "科学", "文明"})
    @ExcelProperty("分类")
    private String type;
    
    @ColumnWidth(17)
    @DateTimeFormat(DatePattern.NORM_DATETIME_PATTERN)
    @ExcelProperty("发布时间*")
    private LocalDateTime pushDate;
    
}
