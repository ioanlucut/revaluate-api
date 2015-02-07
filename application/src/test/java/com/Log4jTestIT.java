package com;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "/applicationContext.xml")
public class Log4jTestIT {

    private static final Logger LOGGER = Logger.getLogger(Log4jTestIT.class);

    @Test
    public void insertAUserWorks() {
        System.out.println("STARTS");
        LOGGER.info("TEST");
        System.out.println("ENDS");
    }
}