package com.revaluate.route;

import com.revaluate.EmailJobBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext__emailJob.xml")
@ActiveProfiles("IT")
public class EmailJobRouterTest {

    @Test
    public void routeWorks() throws Exception {
        EmailJobBootstrap.main(new String[]{});
    }
}