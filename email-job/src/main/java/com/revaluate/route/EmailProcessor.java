package com.revaluate.route;

import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailProcessor {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public void fetchAll() {
        producerTemplate.sendBody("direct:fetchAll", userRepository.findAll());
    }
}