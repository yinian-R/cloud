package com.wymm.sample.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.annotation.WriteSheetParam;
import com.wymm.common.excel.handler.ExcelHandleHelper;
import com.wymm.common.excel.handler.write.DateFormatStyleWriteHandler;
import com.wymm.common.excel.handler.write.TitleStatStyleWriteHandler;
import com.wymm.common.excel.processor.ExcelWriterProcess;
import com.wymm.sample.excel.core.DictOptionWriteHandler;
import com.wymm.sample.excel.model.DownloadData;
import com.wymm.sample.excel.model.DownloadData2;
import com.wymm.sample.excel.model.TemplateExcel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/new")
@RestController
public class ExcelNewController {
    
    @ExcelResponse(
            fileName = "XXX导入模板",
            fileNameTimeSuffix = false,
            template = "excel/demo.xlsx"
    )
    @GetMapping("/downloadTemplate")
    public void downloadTemplate() {
    }
    
    
    @ExcelResponse(
            fileName = "XXX导入模板",
            fileNameTimeSuffix = false,
            inMemory = true,
            writeSheets = @WriteSheetParam(sheetName = "sheet1", head = TemplateExcel.class)
    )
    @GetMapping("/downloadTemplate2")
    public void downloadTemplate2() {
        ExcelHandleHelper.registerWriteHandler(
                new DictOptionWriteHandler(),
                new DateFormatStyleWriteHandler(),
                new TitleStatStyleWriteHandler());
    }
    
    @PostMapping("/uploadExcel")
    public void uploadExcel(MultipartFile file) throws IOException {
        List<Object> objects = EasyExcel.read(file.getInputStream())
                .head(TemplateExcel.class)
                .doReadAllSync();
        
        for (Object object : objects) {
            System.out.println(object);
        }
    }
    
    @ExcelResponse(
            fileName = "XXX记录导出",
            writeSheets = @WriteSheetParam(sheetName = "sheetNameHR", head = DownloadData.class)
    )
    @GetMapping("/download")
    public List<DownloadData> download() {
        return Data.data();
    }
    
    @ExcelResponse(fileName = "XXX记录导出")
    @GetMapping("/downloadByExcelWriterProcess")
    public ExcelWriterProcess downloadByBuilder() {
        return excelWriter -> {
            WriteSheet writeSheet = EasyExcel.writerSheet("配置信息")
                    .head(DownloadData.class)
                    .build();
            excelWriter.write(Data.data(), writeSheet);
            WriteSheet writeSheet2 = EasyExcel.writerSheet("使用记录")
                    .head(DownloadData2.class)
                    .build();
            excelWriter.write(Data.data2(), writeSheet2);
        };
    }
    
    @ExcelResponse(
            fileName = "XXX记录导出", template = "excel/fillComposite.xlsx"
    )
    @GetMapping("/downloadOneExcelMutiFill")
    public ExcelWriterProcess downloadOneExcelMutiFill() {
        List<DownloadData> data = Data.data();
        List<DownloadData> data2 = Data.data();
        return excelWriter -> {
            excelWriter.fill(new FillWrapper("data1", data), EasyExcel.writerSheet("meta")
                    .build()
            );
            excelWriter.fill(new FillWrapper("data2", data2), EasyExcel.writerSheet("meta")
                    .build()
            );
            excelWriter.fill(new FillWrapper("data3", Data.data2()), EasyExcel.writerSheet("meta-data3")
                    .build()
            );
        };
    }
}
