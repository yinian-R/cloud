package com.wymm.common.excel.aop;

import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.handler.ExcelHandleHelper;
import com.wymm.common.excel.processor.WriteProcessor;
import com.wymm.common.excel.util.ExcelException;
import com.wymm.common.excel.vo.ErrorMessage;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class ExcelResponseReturnValueProcessor extends RequestResponseBodyMethodProcessor {
    
    private final List<WriteProcessor> writeProcessorList;
    
    public ExcelResponseReturnValueProcessor(List<HttpMessageConverter<?>> converters, List<WriteProcessor> writeProcessorList) {
        super(converters);
        this.writeProcessorList = writeProcessorList;
    }
    
    /**
     * 处理 @ExcelResponse 声明的方法
     *
     * @param returnType 方法类型
     * @return 如果处理程序支持则返回 true，否则 false
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(ExcelResponse.class) != null;
    }
    
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException, IOException {
        HttpServletResponse response = null;
        try {
            response = webRequest.getNativeResponse(HttpServletResponse.class);
            Assert.state(response != null, "No HttpServletResponse");
            mavContainer.setRequestHandled(true);
            Method method = (Method) returnType.getExecutable();
            
            ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);
            
            writeProcessorList.stream()
                    .filter(v -> v.support(method, excelResponse))
                    .findFirst()
                    .orElseThrow(() -> new ExcelException("Can not find 'WriteHandler' support " + method.getReturnType().getName()))
                    .export(returnValue, response, excelResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ErrorMessage message = new ErrorMessage();
            message.setErrorMessage(e.getMessage());
    
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            super.handleReturnValue(message, returnType, mavContainer, webRequest);
        } finally {
            ExcelHandleHelper.clearWriteHandlers();
        }
    }
    
}
