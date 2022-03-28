package com.wymm.common.core.util;


import com.wymm.common.core.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 响应信息主体
 *
 * @param <T>
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> {
    
    @Getter
    @Setter
    private Integer code;
    
    @Getter
    @Setter
    private String msg;
    
    @Getter
    @Setter
    private T data;
    
    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }
    
    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }
    
    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }
    
    public static <T> R<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }
    
    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }
    
    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }
    
    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }
    
    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    
}
