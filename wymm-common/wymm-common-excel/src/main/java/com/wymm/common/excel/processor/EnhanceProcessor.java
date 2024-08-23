package com.wymm.common.excel.processor;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.config.ExcelConfigProperties;
import com.wymm.common.excel.handler.write.StyleCellStyleStrategy;
import com.wymm.common.excel.handler.write.StyleSheetWriteHandler;
import com.wymm.common.excel.util.ExcelException;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class EnhanceProcessor extends AbstractWriteProcessor {
    
    public EnhanceProcessor(ExcelConfigProperties configProperties) {
        super(configProperties);
    }
    
    /**
     * 是否支持
     *
     * @param executable    executable
     * @param excelResponse excelResponse
     * @return true 支持
     */
    @Override
    public boolean support(Method executable, ExcelResponse excelResponse) {
        Class<?> methodReturnType = executable.getReturnType();
        return Consumer.class.isAssignableFrom(methodReturnType);
    }
    
    /**
     * 校验注释
     *
     * @param resultValue   resultValue
     * @param excelResponse ExcelResponse
     */
    @Override
    public void check(Object resultValue, ExcelResponse excelResponse) {
        if (ObjectUtils.isEmpty(excelResponse.fileName())) {
            throw new ExcelException("@ResponseExcel fileName 不能为空");
        }
    }
    
    @Override
    protected void setDefaultStyle(ExcelResponse excelResponse, ExcelWriterBuilder excelWriterBuilder) {
        if (ObjectUtils.isNotEmpty(excelResponse.template())) {
            //  使用模板时，设置默认样式
            excelWriterBuilder.registerWriteHandler(new StyleCellStyleStrategy(true, false));
        } else {
            //  不使用模板时，设置默认样式
            excelWriterBuilder.registerWriteHandler(new StyleCellStyleStrategy());
            excelWriterBuilder.registerWriteHandler(new StyleSheetWriteHandler());
        }
    }
    
    /**
     * 导出
     *
     * @param resultValue   resultValue
     * @param response      response
     * @param excelResponse 注解
     */
    @Override
    public void export(Object resultValue, HttpServletResponse response, ExcelResponse excelResponse) {
        this.check(resultValue, excelResponse);
        
        Consumer<ExcelWriter> resultFunction = (Consumer<ExcelWriter>) resultValue;
        
        this.initResponse(response, excelResponse);
        
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(response, excelResponse);
            
            resultFunction.accept(excelWriter);
        } finally {
            if (excelWriter != null) {
                // 关闭流
                excelWriter.finish();
            }
        }
    }
    
}
