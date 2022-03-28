package com.wymm.common.excel.aop;

import com.wymm.common.excel.annotation.ExcelResponse;
import com.wymm.common.excel.handler.SheetWriteHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExcelResponseReturnValueHandler implements HandlerMethodReturnValueHandler {
    
    private final List<SheetWriteHandler> sheetWriteHandlerList;
    
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
                                  NativeWebRequest webRequest) {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        mavContainer.setRequestHandled(true);
        
        ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);
        
        sheetWriteHandlerList.stream().filter(v -> v.support(returnValue))
                .findFirst()
                .ifPresent(handler -> handler.export(returnValue, response, excelResponse));
    }
}
