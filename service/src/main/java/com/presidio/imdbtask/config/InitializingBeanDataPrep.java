package com.presidio.imdbtask.config;

import com.presidio.imdbtask.service.DataGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializingBeanDataPrep implements InitializingBean {

    @Autowired
    private DataGenerator generator;

    @Override
    public void afterPropertiesSet() throws Exception {
        generator.generate();
    }
}