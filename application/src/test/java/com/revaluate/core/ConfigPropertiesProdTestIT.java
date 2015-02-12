package com.revaluate.core;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class ConfigPropertiesProdTestIT {

    @Resource
    private ConfigProperties configProperties;

    @BeforeClass
    public static void setUp() {
        System.setProperty(ConfigProperties.ENVIRONMENT, "prod");
    }

    @AfterClass
    public static void tearDown() {
        System.clearProperty(ConfigProperties.ENVIRONMENT);
    }

    @Test
    @DirtiesContext(hierarchyMode = DirtiesContext.HierarchyMode.CURRENT_LEVEL)
    public void configPropertiesWorks() {
        Assert.assertNotNull(configProperties);
        Assert.assertTrue(configProperties.isProduction());
        Assert.assertNotNull(configProperties.getAuthTokenHeaderKey());
        Assert.assertNotNull(configProperties.getIssuer());
        Assert.assertNotNull(configProperties.getShared());
    }
}