package com.zsx.test;

import com.zsx.bean.AutoBean;
import com.zsx.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ManualRegisterBeanTest {

    @Autowired
    private AutoBean autoBean;

    @Autowired
    private UserService userService;

    @Test
    void testPrint() {
        Assertions.assertNotNull(autoBean.print());
    }

    @Test
    void testGetUserName() {
        Assertions.assertEquals("zhangsan", userService.getUserName());
    }
}
