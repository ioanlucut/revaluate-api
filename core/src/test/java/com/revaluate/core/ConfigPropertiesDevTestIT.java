package com.revaluate.core;

import com.revaluate.core.bootstrap.ConfigProperties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__core.xml")
public class ConfigPropertiesDevTestIT {

    @Resource
    private ConfigProperties configProperties;

    @BeforeClass
    public static void setUp() {
        System.setProperty(ConfigProperties.ENVIRONMENT, "dev");
    }

    @AfterClass
    public static void tearDown() {
        System.clearProperty(ConfigProperties.ENVIRONMENT);
    }

    @Test
    @DirtiesContext(hierarchyMode = DirtiesContext.HierarchyMode.CURRENT_LEVEL)
    public void configPropertiesWorks() {
        assertThat(configProperties).isNotNull();
        assertThat(configProperties.isProduction()).isFalse();
        assertThat(configProperties.getAuthTokenHeaderKey()).isNotNull();
        assertThat(configProperties.getIssuer()).isNotNull();
        assertThat(configProperties.getShared()).isNotNull();
    }
}