package com.mana.sys.controller;

import com.mana.sys.bean.CustomResponse;
import com.mana.sys.constants.RoleConstants;
import com.mana.sys.constants.SysConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/29
 * @description: unit tests for UserController
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddUser() throws Exception {
        String base64AccountInfo = getAccountBase64(123456l, RoleConstants.ADMIN);
        String endpointsJson = "{}";
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(endpointsJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        endpointsJson = "{\"endpoints\":" +
                "[{\"userId\":123456,\"endpoint\" :[\"resource D\",\"resource F\"]}," +
                "{\"userId\":123457,\"endpoint\": [\"resource A\",\"resource B\",\"resource C\"]}," +
                "{\"userId\":123458,\"endpoint\": [\"resource A\",\"resource B\",\"resource C\"]}]}";
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(endpointsJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
        testGetAccessAfterAdd("/user/resource D", base64AccountInfo);
    }

    @Test
    public void testAddUserTooLongId() throws Exception {
        String base64AccountInfo = getAccountBase64(123456l, RoleConstants.ADMIN);
        String endpointsJson = "{\"endpoints\":" +
                "[{\"userId\":12345711111111111111111111,\"endpoint\": [\"resource A\"]}]}";
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(endpointsJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void testAddUserNonAdminAccount() throws Exception {
        String base64AccountInfo = getAccountBase64(999999l, RoleConstants.USER);
        String endpointsJson = "{\"endpoints\":" +
                "[{\"userId\":123456,\"endpoint\": [\"resource A\"]}]}";
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(endpointsJson))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        CustomResponse.createStringRespone(HttpStatus.UNAUTHORIZED,"no authority!")));
        base64AccountInfo = getAccountBase64(999999l,"anonymous");
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(endpointsJson))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        CustomResponse.createStringRespone(HttpStatus.UNAUTHORIZED,"no authority!")));
    }

    @Test
    public void testGetAccess() throws Exception {
        String base64AccountInfo = getAccountBase64(123456l, RoleConstants.ADMIN);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/ resource D")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        CustomResponse.createStringRespone(HttpStatus.UNAUTHORIZED, SysConstants.FAILER)));
    }

    @Test
    public void testGetAccessIllegal() throws Exception {
        String base64AccountInfo = getAccountBase64(123456l, RoleConstants.ADMIN);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/ ")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        CustomResponse.createStringRespone(HttpStatus.FORBIDDEN, SysConstants.ILLEGAL)));
        // andExpect of response status is dependent on the version of spring-boot
        mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo));
    }

    private String getAccountBase64(long userId, String role) {
        String accountJson = "{" +
                "\"userId\": " + userId + "," +
                "\"accountName\": \"XXXXXXX\"," +
                "\"role\": \"" + role + "\"" +
                "}";
        return Base64.getEncoder().encodeToString(accountJson.getBytes());
    }

    private void testGetAccessAfterAdd(String url ,String base64AccountInfo) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header(SysConstants.HEADER_ACCOUNT, base64AccountInfo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        CustomResponse.createStringRespone(HttpStatus.OK, SysConstants.SUCCESS)));
    }
}