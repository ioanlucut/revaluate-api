package com.revaluate.slack;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__slack__test.xml")
@ActiveProfiles("IT")
public class SlackServiceImpl_Mint_TestIT {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private SlackService slackService;

    @Test
    public void importFromMint() throws Exception {
        String clientId = "2151987168.10687444405";
        String clientSecret = "9efca5a3f6c459259e950c715c3433e2";
        String clientSecrets = "http://localhost:3000/";
        /*"xoxp-2151987168-2873169224-10441274036-692107";*/

    }
}