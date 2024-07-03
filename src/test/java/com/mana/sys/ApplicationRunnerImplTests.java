package com.mana.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/29
 * @description: unit tests for ApplicationRunnerImpl
 */
@SpringBootTest
public class ApplicationRunnerImplTests {
    @Autowired
    private ApplicationRunner applicationRunner;

    @Test
    public void testRun() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            applicationRunner.run(null);
        });
    }
}