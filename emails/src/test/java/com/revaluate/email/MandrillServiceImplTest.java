package com.revaluate.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__emailApi__test.xml")
@ActiveProfiles("IT")
public class MandrillServiceImplTest {

    @Autowired
    private MandrillService mandrillService;

    @Test
    public void serviceIsNotNull() {
        assertThat(mandrillService).isNotNull();
        assertThat(mandrillService.getApi()).isNotNull();
    }
}