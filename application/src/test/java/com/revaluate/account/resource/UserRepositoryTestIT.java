package com.revaluate.account.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath*:spring-config.application/spring-context-application.xml")
public class UserRepositoryTestIT {

    @Autowired
    private UserResource userResource;

    @Test
    public void emptyEmail() {
        userResource.isUnique(null);
    }
    /*
    @Test
    public void insertAUserWorks() {
        User user = new User();
        user.setEmail("email");
        user.setFirstName("ilu");
        user.setLastName("asda");
        user.setPassword("abcd");

        User savedUser = repository.save(user);
        MatcherAssert.assertThat(savedUser, Matchers.notNullValue());
        User foundUser = repository.findOne(savedUser.getId());
        MatcherAssert.assertThat(foundUser, Matchers.notNullValue());
    }*/
}