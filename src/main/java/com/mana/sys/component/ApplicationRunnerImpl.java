package com.mana.sys.component;

import com.mana.sys.constants.SysConstants;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: mkdir for endpoints use ApplicationRunner
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        int retryLimits = 5;
        for (int i = 0; i < retryLimits; i++) {
            try {
                File dir = new File(SysConstants.RESOURCE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                return;
            } catch (Throwable th) {
                // need to configure log module to print error log
                System.out.println("mkdir for endpoints Throwable:" + th.getMessage());
            }
        }
    }
}