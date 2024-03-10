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
    
    public static <T> R<T> error() {
        return restResult(null, CommonConstants.BAD_REQUEST, null);
    }
    
    public static <T> R<T> error(String msg) {
        return restResult(null, CommonConstants.BAD_REQUEST, msg);
    }
    
    public static <T> R<T> error(T data) {
        return restResult(data, CommonConstants.BAD_REQUEST, null);
    }
    
    public static <T> R<T> error(T data, String msg) {
        return restResult(data, CommonConstants.BAD_REQUEST, msg);
    }
    
    /**
     * @param data 数据对象
     * @param code {@link CommonConstants}
     * @param msg  提醒语句
     * @return {@link R}
     */
    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    
}
