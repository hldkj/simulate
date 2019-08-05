package com.sft.simulate.exceptions;


import com.sft.simulate.pojo.ArgumentInvalidResult;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2019-04-15.
 * @desc
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response<Void> httpMediaTypeNotSupportedException(HttpServletRequest request, Exception ex) {
        log.warn("http请求类型异常{}", ex.getMessage());
        return Response.fail(9999, "当前请求类型不支持");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<Void> httpRequestMethodNotSupportedException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        log.warn("请求方式异常{}", ex.getMessage());
        return Response.fail(9999, "当前请求方式不支持");
    }

    @ExceptionHandler(ServiceException.class)
    public Response<Void> serviceExecption(HttpServletRequest request, Exception ex) {
        log.warn("业务异常{}", ex.getMessage());
        ServiceException execption = (ServiceException) ex;
        return Response.fail(execption.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Response<Void> bindExecption(HttpServletRequest request, Exception ex) {
        log.warn("参数异常{}", ex.getMessage());
        BindException exception = (BindException) ex;
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArguments.add(invalidArgument);
        }
        return Response.fail(9999, JacksonUtils.encode(invalidArguments));
    }


    @ExceptionHandler(Exception.class)
    public Response<Void> unknownExecption(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        log.warn("未知异常{}", ex.getMessage());
        return Response.fail(9999, "服务器异常，请联系管理员");
    }
}
