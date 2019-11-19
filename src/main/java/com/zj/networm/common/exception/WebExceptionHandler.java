package com.zj.networm.common.exception;

import com.zj.networm.common.result.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {

    Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVO handlerException(BizException e){
        log.error(e.getMessage(),e);
        ResponseVO response = new ResponseVO(e.getErrorCode(),e.getErrorMessage());
        return response;
    }


}
