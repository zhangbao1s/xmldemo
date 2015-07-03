package com.adchina.api.advice;

import com.adchina.api.bean.Response;
import com.adchina.api.exception.ServiceException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 用于处理所有的异常情况
 *
 * @author huangyong
 * @since 1.0.0
 */
@ControllerAdvice
@ResponseBody
@ResponseStatus(HttpStatus.OK)
public class ExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return new Response().failure("request_method_not_supported");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return new Response().failure("content_type_not_supported");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        return new Response().failure("required_parameter_is_not_present");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(Exception e) {
        logger.error("参数解析失败", e);
        return new Response().failure("could_not_read_json");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new Response().failure(message);
    }

    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new Response().failure(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new Response().failure("parameter:" + message);
    }

    @ExceptionHandler(ServiceException.class)
    public Response handleServiceException(RuntimeException e) {
        logger.error("服务运行异常", e);
        return new Response().failure(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return new Response().failure("validation_exception");
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        logger.error("通用异常", e);
        return new Response().failure("exception");
    }
}
