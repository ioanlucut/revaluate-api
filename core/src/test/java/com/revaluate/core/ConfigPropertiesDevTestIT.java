package com.revaluate.core;

import com.revaluate.core.bootstrap.ConfigProperties;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
        MatcherAssert.assertThat(configProperties, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(configProperties.isProduction(), Matchers.is(false));

        MatcherAssert.assertThat(configProperties.getAuthTokenHeaderKey(), Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(configProperties.getIssuer(), Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(configProperties.getShared(), Matchers.is(Matchers.notNullValue()));
    }
}