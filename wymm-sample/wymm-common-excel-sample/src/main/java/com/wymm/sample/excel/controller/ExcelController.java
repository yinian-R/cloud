package com.wymm.sample.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.handler.ExcelWriterProcess;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExcelController {
    
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
            sheets = @ExcelResponse.Sheet(sheetName = "sheetNameHR", head = DownloadData.class)
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
            sheets = @ExcelResponse.Sheet(sheetName = "sheetNameHR")
    )
    @GetMapping("/downloadByAnCSV")
    public List<DownloadData> downloadByAnCSV() {
        return data();
    }
    
    /**
     * 测试导出 csv
     */
    @ExcelResponse(fileName = "测试导出", suffix = ExcelTypeEnum.XLS,
            sheets = @ExcelResponse.Sheet(sheetName = "sheetNameHR")
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
    @ExcelResponse(fileName = "测试导出")
    @GetMapping("/downloadError")
    public DownloadData error() {
        return null;
    }
    
    /**
     * 测试导出 xlsx
     */
    @ExcelResponse(
            fileName = "测试导出", password = "123456", template = "excel/demo.xlsx",
            sheets = @ExcelResponse.Sheet(sheetName = "sheetNameHR", head = DownloadData.class)
    )
    @GetMapping("/downloadByAnOthor")
    public List<DownloadData> downloadByAnOther() {
        return data();
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
    
    /**
     * 使用 ExcelWriterProcess 导出
     */
    @ExcelResponse(fileName = "测试导出")
    @GetMapping("/downloadByExcelWriterProcess")
    public ExcelWriterProcess downloadByBuilder() {
        return excelWriter -> {
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet名称")
                    .head(DownloadData.class)
                    .build();
            excelWriter.write(data(), writeSheet);
        };
    }
    
    /**
     * 使用 Map<Integer, List> 导出
     */
    @ExcelResponse(
            fileName = "测试导出",
            sheets = {
                    @ExcelResponse.Sheet(sheetNo = 0, sheetName = "sheetName1", head = DownloadData.class),
                    @ExcelResponse.Sheet(sheetNo = 1, sheetName = "sheetName2", head = DownloadData.class),
                    @ExcelResponse.Sheet(sheetNo = 2, sheetName = "sheetName3", head = DownloadData.class)
            }
    )
    @GetMapping("/downloadMany")
    public Map<Integer, List<DownloadData>> downloadMany() {
        Map<Integer, List<DownloadData>> map = new HashMap<>();
        map.put(0, data());
        map.put(1, data());
        map.put(2, data());
        return map;
    }
    
}
