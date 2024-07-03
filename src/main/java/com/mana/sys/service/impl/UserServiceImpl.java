package com.mana.sys.service.impl;

import com.mana.sys.bean.UserEndpoint;
import com.mana.sys.constants.SysConstants;
import com.mana.sys.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: UserService realization
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void addAccess(List<UserEndpoint> userEndpoints) throws IOException {
        if (CollectionUtils.isEmpty(userEndpoints)) {
            throw new RuntimeException("endpoints parameter empty!");
        }
        FileWriter fw = null;
        BufferedWriter bufferedWriter = null;
        for (UserEndpoint userEndpoint : userEndpoints) {
            String pathStr = SysConstants.RESOURCE_DIR + File.separator
                    + userEndpoint.getUserId() + SysConstants.FILE_SUFFIX;
            List<String> allLines = new ArrayList<>();
            if (Files.exists(Paths.get(pathStr))) {
                allLines = Files.readAllLines(Paths.get(pathStr));
            }
            Set<String> allResourceSet = new HashSet<>(allLines);
            fw = new FileWriter(pathStr, true);
            bufferedWriter = new BufferedWriter(fw);
            for (String endpoint : userEndpoint.getEndpoint()) {
                if (!allResourceSet.contains(endpoint)) {
                    bufferedWriter.write(endpoint);
                    bufferedWriter.newLine();
                    allResourceSet.add(endpoint);
                }
            }
            bufferedWriter.flush();
            if (fw != null) {
                fw.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    @Override
    public boolean checkAccess(long userId, String resource) throws IOException {
        if (Strings.isBlank(resource)) {
            return false;
        }
        String pathStr = SysConstants.RESOURCE_DIR + File.separator
                + userId + SysConstants.FILE_SUFFIX;
        if (Files.exists(Paths.get(pathStr))) {
            List<String> allLines = Files.readAllLines(Paths.get(pathStr));
            if (allLines.contains(resource)) {
                return true;
            }
        }
        return false;
    }
}
