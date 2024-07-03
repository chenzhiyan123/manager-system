package com.mana.sys.service;


import com.mana.sys.bean.UserEndpoint;

import java.io.IOException;
import java.util.List;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: user service
 */
public interface UserService {
    void addAccess(List<UserEndpoint> userEndpoints) throws IOException;

    boolean checkAccess(long userId, String resource) throws IOException;
}