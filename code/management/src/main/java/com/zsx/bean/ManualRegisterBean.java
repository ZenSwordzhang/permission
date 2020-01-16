package com.zsx.bean;

import com.zsx.service.impl.UserServiceImpl;
import com.zsx.util.ManualRegisterBeanUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ManualRegisterBean {

    public ManualRegisterBean(ConfigurableApplicationContext applicationContext) {
        registerManualBean(applicationContext);
    }

    /**
     * 手动注册自定义地bean
     * @param applicationContext
     */
    private void registerManualBean(ConfigurableApplicationContext applicationContext) {
        // 主动注册Bean
        ManualRegisterBeanUtil.registerBean(applicationContext, "autoBean", AutoBean.class);
        ManualRegisterBeanUtil.registerBean(applicationContext, "userService", UserServiceImpl.class);
    }
}
