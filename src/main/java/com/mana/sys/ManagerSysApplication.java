package com.mana.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/29
 * @description: application start,System always return a readable error message when an error occurs with ExceptionController.
 */
@SpringBootApplication
public class ManagerSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerSysApplication.class, args);
    }

}