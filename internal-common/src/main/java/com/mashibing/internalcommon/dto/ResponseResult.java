package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***********************
 * @Description: 通用返回值处理类 <BR>
 * @author: zhao.song
 * @since: 2021/5/11 7:35
 * @version: 1.0
 ***********************/
@Data
//chain含义为链式,设置为true,则setter方法返回当前对象, 做链式塞值处理
@Accessors(chain = true)
//压制所有异常
@SuppressWarnings("all")
@Builder //类构建器 , 类似Accessors注解
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = -2073390651767969040L;

    private int code;
    private String message;
    private T data;

    /**
     * Description: 返回成功数据(status:1) <BR>
     *
     * @param data: 数据内容
     * @param <T>:  数据类型
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/11 7:42
     */
    public static <T> ResponseResult success(T data) {
        return ResponseResult.builder().code(CommonStatusEnum.SUCCESS.getCode())
                .message(CommonStatusEnum.SUCCESS.getValue()).data(data).build();
    }

    /**
     * Description: 返回错误数据 <BR>
     *
     * @param data: 数据内容
     * @param <T>:  数据类型
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/11 7:42
     */
    public static <T> ResponseResult fail(T data) {
        return ResponseResult.builder().code(CommonStatusEnum.FAIL.getCode())
                .message(CommonStatusEnum.FAIL.getValue()).data(data).build();
    }

    /**
     * Description: 自定义返回错误信息 <BR>
     *
     * @param code:
     * @param message:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/11 7:43
     */
    public static <T> ResponseResult fail(int code, String message) {
        return ResponseResult.builder().code(code).message(message).build();
    }

    /**
     * Description: 自定义返回错误信息 <BR>
     *
     * @param code:
     * @param message:
     * @param data:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/11 7:43
     */
    public static <T> ResponseResult fail(int code, String message, String data) {
        return ResponseResult.builder().code(code).message(message).data(data).build();
    }
}
