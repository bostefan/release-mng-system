package com.holycode.neon;

import com.holycode.neon.controller.ReleaseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private ReleaseController releaseController;

    @Test
    public void contexLoads() {
        assertThat(releaseController).isNotNull();
    }
}
