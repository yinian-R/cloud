package com.wymm.sample.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.annotation.FillSheetParam;
import com.wymm.common.excel.annotation.WriteSheetParam;
import com.wymm.common.excel.handler.ExcelHandleHelper;
import com.wymm.common.excel.handler.write.DateFormatStyleWriteHandler;
import com.wymm.common.excel.handler.write.TitleStatStyleWriteHandler;
import com.wymm.common.excel.metadata.FillCompositeWrapper;
import com.wymm.sample.excel.core.DictOptionWriteHandler;
import com.wymm.sample.excel.model.DownloadData;
import com.wymm.sample.excel.model.TemplateExcel;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
public class ExcelController {
    
    /**
     * 原生代码
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        
        EasyExcel.write(response.getOutputStream()).sheet("模板").doWrite(data());
    }
    
    /**
     * 测试导出 xlsx
     */
    @ExcelResponse(
            fileName = "测试导出",
            writeSheets = @WriteSheetParam(sheetName = "sheetNameHR", head = DownloadData.class)
    )
    @GetMapping("/downloadByAn")
    public List<DownloadData> downloadByAn() {
        return data();
    }
    
    /**
     * 测试导出 csv
     */
    @ExcelResponse(
            fileName = "测试导出", suffix = ExcelTypeEnum.CSV,
            writeSheets = @WriteSheetParam(head = DownloadData.class, sheetName = "sheetNameHR")
    )
    @GetMapping("/downloadByAnCSV")
    public List<DownloadData> downloadByAnCSV() {
        return data();
    }
    
    /**
     * 测试导出 csv
     */
    @ExcelResponse(fileName = "测试导出", suffix = ExcelTypeEnum.XLS,
            writeSheets = @WriteSheetParam(head = DownloadData.class, sheetName = "sheetNameHR")
    )
    @GetMapping("/downloadByAnXLS")
    public List<DownloadData> downloadByAnXLS() {
        return data();
    }
    
    /**
     * 测试导出 错误的返回类型
     */
    @ExcelResponse(fileName = "测试导出", suffix = ExcelTypeEnum.XLS)
    @GetMapping("/downloadErrorResultType")
    public DownloadData downloadByResultType() {
        return data().get(0);
    }
    
    /**
     * 测试导出 错误的返回值
     */
    @ExcelResponse(fileName = "测试导出",
            writeSheets = @WriteSheetParam(sheetName = "sheetNameHR", head = DownloadData.class)
    )
    @GetMapping("/downloadError")
    public List<List<DownloadData>> error() {
        List<List<DownloadData>> list = new ArrayList<>();
        list.add(data());
        list.add(data());
        return list;
    }
    
    /**
     * 测试导出 xlsx
     */
    @ExcelResponse(
            fileName = "测试使用模板写入导出", fileNameTimeSuffix = false, template = "excel/demo.xlsx",
            writeSheets = @WriteSheetParam(head = DownloadData.class, sheetName = "meta")
    )
    @GetMapping("/downloadByAnOthor")
    public List<DownloadData> downloadByAnOther() {
        return data();
    }
    
    
    @ExcelResponse(
            fileName = "测试使用模板填充导出", fileNameTimeSuffix = false, template = "excel/fill.xlsx",
            inMemory = true,
            fillSheets = @FillSheetParam(sheetName = "meta")
    )
    @GetMapping("/downloadFill")
    public List<DownloadData> downloadFill() {
        return data();
    }
    
    @ExcelResponse(
            fileName = "测试使用模板多列表填充导出", fileNameTimeSuffix = false, template = "excel/fillComposite.xlsx",
            fillSheets = @FillSheetParam(sheetName = "meta")
    )
    @GetMapping("/downloadCompositeFill")
    public FillCompositeWrapper downloadCompositeFill() {
        FillCompositeWrapper wrapper = new FillCompositeWrapper();
        wrapper.addFillData("data1", data());
        wrapper.addFillData("data2", data());
        return wrapper;
    }
    
    @ExcelResponse(
            fileName = "测试下载模板",
            fileNameTimeSuffix = false,
            template = "excel/fillComposite.xlsx",
            fillSheets = @FillSheetParam
    )
    @GetMapping("/downloadTemplate")
    public void downloadTemplate() {
        //return Collections.emptyList();
    }
    
    
    /**
     * 测试导出具有下拉框、日期格式、必填符号的 xlsx
     */
    @ExcelResponse(
            fileName = "XXX模板",
            fileNameTimeSuffix = false,
            inMemory = true,
            writeSheets = @WriteSheetParam(sheetName = "sheet1", head = TemplateExcel.class)
    )
    @GetMapping("/downloadDictTemplate")
    public List<TemplateExcel> downloadDictTemplate() {
        ExcelHandleHelper.registerWriteHandler(
                new DictOptionWriteHandler(),
                new DateFormatStyleWriteHandler(),
                new TitleStatStyleWriteHandler());
        return Collections.emptyList();
    }
    
    @PostMapping("/uploadDictTemplate")
    public void uploadDictTemplate(MultipartFile file) throws IOException {
        List<Object> objects = EasyExcel.read(file.getInputStream())
                .head(TemplateExcel.class)
                .doReadAllSync();
    
        for (Object object : objects) {
            System.out.println(object);
        }
    }
    
    private List<DownloadData> data() {
        List<DownloadData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setTime(LocalDateTime.now());
            list.add(data);
        }
        return list;
    }
}
