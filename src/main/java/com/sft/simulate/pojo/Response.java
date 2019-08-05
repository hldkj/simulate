package com.sft.simulate.pojo;

import com.sft.simulate.enums.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by yuyidi on 2019-04-15.
 * @desc
 */
@Getter
@Setter
public class Response<T> {

    private int code;
    private String message;
    private T data;


    public Response() {
    }

    private Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>(1000, "响应成功", data);
        return response;
    }

    public static <T> Response<T> fail(int code, String message) {
        Response<T> response = new Response<>(code, message, null);
        return response;
    }

    public static <T> Response<T> fail(ErrorCode errorCode) {
        Response<T> response = new Response<>(errorCode.code(), errorCode.message(), null);
        return response;
    }

    public T getData() {
        return data;
    }

}
