package com.mana.sys.component;

import com.mana.sys.bean.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: chenzhiyan
 * @createDate: 2024/07/01
 * @description: make sure that system always return a readable error message when an error occurs
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleException(Throwable e) {
        return CustomResponse.createRespone(HttpStatus.INTERNAL_SERVER_ERROR,"system error:" + e.getMessage());
    }
}
