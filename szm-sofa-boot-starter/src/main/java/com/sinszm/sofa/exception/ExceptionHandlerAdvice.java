package com.sinszm.sofa.exception;

import com.sinszm.sofa.response.Result;
import com.sinszm.sofa.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> _400(Exception e) {
        return ResultUtil.fail(_400, e);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result<Object> _404(NoHandlerFoundException e) {
        return ResultUtil.fail(_404, e);
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Result<Object> _405(HttpRequestMethodNotSupportedException e) {
        return ResultUtil.fail(_405, e);
    }

    @ExceptionHandler({
            HttpMediaTypeNotAcceptableException.class
    })
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public Result<Object> _406(HttpMediaTypeNotAcceptableException e) {
        return ResultUtil.fail(_406, e);
    }

    @ExceptionHandler({
            SQLException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> sql(SQLException e) {
        return ResultUtil.fail(SQL_ERROR, e);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> fail(MethodArgumentNotValidException e) {
        return ResultUtil.fail(FAIL, e);
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> fail(IllegalArgumentException e) {
        return ResultUtil.fail(FAIL, e);
    }

    @ExceptionHandler({
            ApiException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> api(ApiException e) {
        return ResultUtil.fail(e.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler({
            Throwable.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> other(Throwable e) {
        return ResultUtil.fail(SYSTEM_ERROR, e);
    }

}
