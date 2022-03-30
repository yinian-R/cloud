## EasyExcel 简化封装

简化 Controller 使用 EasyExcel 导出 Excel

导出数据到 xlsx 中：
```
@ExcelResponse(
        fileName = "测试导出",
        sheets = @ExcelResponse.Sheet(sheetName = "sheetNameHR", head = DownloadData.class)
)
@GetMapping("/downloadByAn")
public List<DownloadData> downloadByAn() {
    return data();
}
```


导出数据到多个 sheet 中：
```
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
```

若有以上导出都无法满足的场景，可参考以下自定义：
```
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
```