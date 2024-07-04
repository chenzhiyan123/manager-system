package com.mana.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mana.sys.bean.Account;
import com.mana.sys.bean.CustomResponse;
import com.mana.sys.bean.UserEndpointList;
import com.mana.sys.constants.SysConstants;
import com.mana.sys.constants.RoleConstants;
import com.mana.sys.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: Controller for user requests
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/admin/addUser")
    public ResponseEntity<String> addUser(@RequestHeader(SysConstants.HEADER_ACCOUNT) String account,
                                          @RequestBody UserEndpointList userEndpoints) {
        String accountJson = Strings.EMPTY;
        try {
            accountJson = new String(Base64.getDecoder().decode(account));
        } catch (IllegalArgumentException ex1) {
            return CustomResponse.createRespone(HttpStatus.NOT_ACCEPTABLE, "IllegalArgumentException account!");
        }
        ObjectMapper mapperAccount = new ObjectMapper();
        Account accountBean = null;
        try {
            accountBean = mapperAccount.readValue(accountJson, Account.class);
        } catch (JsonProcessingException ex2) {
            return CustomResponse.createRespone(HttpStatus.NOT_ACCEPTABLE, "IllegalArgumentException endpoints!");
        }
        if (RoleConstants.ADMIN.equals(accountBean.getRole())) {
            try {
                userService.addAccess(userEndpoints.getEndpoints());
            } catch (IOException ex3) {
                return CustomResponse.createRespone(HttpStatus.INTERNAL_SERVER_ERROR, "data write error!");
            }
            return CustomResponse.createRespone(HttpStatus.OK, SysConstants.SUCCESS);
        }
        return CustomResponse.createRespone(HttpStatus.UNAUTHORIZED,"no authority!");
    }

    @RequestMapping("/user/{resource}")
    public ResponseEntity<String> getAccess(@RequestHeader(SysConstants.HEADER_ACCOUNT) String account,
                            @PathVariable("resource") String resource) {
        if (Strings.isBlank(resource)) {
            return CustomResponse.createRespone(HttpStatus.FORBIDDEN, SysConstants.ILLEGAL);
        }
        String accountJson = Strings.EMPTY;
        try {
            accountJson = new String(Base64.getDecoder().decode(account));
        } catch (IllegalArgumentException ex1) {
            return CustomResponse.createRespone(HttpStatus.NOT_ACCEPTABLE, "IllegalArgumentException account!");
        }
        ObjectMapper mapperAccount = new ObjectMapper();
        Account accountBean = null;
        try {
            accountBean = mapperAccount.readValue(accountJson, Account.class);
        } catch (JsonProcessingException ex2) {
            return CustomResponse.createRespone(HttpStatus.NOT_ACCEPTABLE, "IllegalArgumentException endpoints!");
        }
        boolean allowAccess = false;
        try {
            allowAccess = userService.checkAccess(accountBean.getUserId(), resource);
        } catch (IOException ex3) {
            return CustomResponse.createRespone(HttpStatus.INTERNAL_SERVER_ERROR, "data read error!");
        }
        if (allowAccess) {
            return CustomResponse.createRespone(HttpStatus.OK, SysConstants.SUCCESS);
        }
        return CustomResponse.createRespone(HttpStatus.UNAUTHORIZED, SysConstants.FAILER);
    }
}