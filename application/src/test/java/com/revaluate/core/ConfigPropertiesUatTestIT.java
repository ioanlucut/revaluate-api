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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ConfigPropertiesUatTestIT {

    @Resource
    private ConfigProperties configProperties;

    @BeforeClass
    public static void setUp() {
        System.setProperty(ConfigProperties.ENVIRONMENT, "uat");
    }

    @AfterClass
    public static void tearDown() {
        System.clearProperty(ConfigProperties.ENVIRONMENT);
    }

    @Test
    @DirtiesContext(hierarchyMode = DirtiesContext.HierarchyMode.CURRENT_LEVEL)
    public void configPropertiesWorks() {
        assertThat(configProperties, is(notNullValue()));
        assertThat(configProperties.isProduction(), is(false));

        assertThat(configProperties.getAuthTokenHeaderKey(), is(notNullValue()));
        assertThat(configProperties.getIssuer(), is(notNullValue()));
        assertThat(configProperties.getShared(), is(notNullValue()));
    }
}