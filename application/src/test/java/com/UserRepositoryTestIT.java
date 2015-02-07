package com;

import com.revaluate.account.model.User;
import com.revaluate.account.repository.UserRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserRepositoryTestIT {

    @Autowired
    private UserRepository repository;

    @Test
    public void insertAUserWorks() {
        User user = new User();
        user.setEmail("email");
        user.setFirstName("ilu");
        user.setLastName("asda");
        user.setPassword("abcd");

        User savedUser = repository.save(user);
        Assert.assertNotNull(savedUser);
        User foundUser = repository.findOne(savedUser.getId());
        Assert.assertNotNull(foundUser);
    }
}