package com.mana.sys.bean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: custom response for system
 */
public class CustomResponse {
    private int code;
    private String message;

    public CustomResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<String> createRespone(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                 getString(status.value(), message));
    }

    public static String createStringRespone(HttpStatus status, String message) {
        return getString(status.value(), message);
    }

    private static String getString(int code, String message) {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}