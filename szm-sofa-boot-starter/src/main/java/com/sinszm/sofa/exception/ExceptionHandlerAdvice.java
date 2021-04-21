package com.sinszm.sofa.exception;

import com.sinszm.sofa.response.Result;
import com.sinszm.sofa.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static com.sinszm.sofa.response.StatusCode.*;

/**
 * 异常捕获
 * @author sinszm
 */
@Slf4j
@ControllerAdvice(annotations = {
        RestController.class,
        Controller.class
})
public class ExceptionHandlerAdvice {

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            TypeMismatchException.class,
            MissingServletRequestParameterException.class
    })
    public Result<Object> _400(HttpServletRequest request, Exception e) {
        return ResultUtil.fail(_400, e);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class
    })
    public Result<Object> _404(HttpServletRequest request, NoHandlerFoundException e) {
        return ResultUtil.fail(_404, e);
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public Result<Object> _405(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        return ResultUtil.fail(_405, e);
    }

    @ExceptionHandler({
            HttpMediaTypeNotAcceptableException.class
    })
    public Result<Object> _406(HttpServletRequest request, HttpMediaTypeNotAcceptableException e) {
        return ResultUtil.fail(_406, e);
    }

    @ExceptionHandler({
            SQLException.class
    })
    public Result<Object> sql(HttpServletRequest request, SQLException e) {
        return ResultUtil.fail(SQL_ERROR, e);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public Result<Object> fail(HttpServletRequest request, MethodArgumentNotValidException e) {
        return ResultUtil.fail(FAIL, e);
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public Result<Object> fail(HttpServletRequest request, IllegalArgumentException e) {
        return ResultUtil.fail(FAIL, e);
    }

    @ExceptionHandler({
            ApiException.class
    })
    public Result<Object> api(HttpServletRequest request, ApiException e) {
        return ResultUtil.fail(e.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler({
            Throwable.class
    })
    public Result<Object> other(HttpServletRequest request, Throwable e) {
        return ResultUtil.fail(SYSTEM_ERROR, e);
    }

}
