package com.revaluate.email;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.controller.MandrillMessagesApi;
import com.revaluate.core.bootstrap.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MandrillServiceImpl implements MandrillService {

    @Autowired
    private ConfigProperties configProperties;

    /**
     * Mandrill API component
     */
    private MandrillApi mandrillApi;

    @PostConstruct
    private void initialize() {
        mandrillApi = new MandrillApi(configProperties.getMandrillAppKey());
    }

    @Override
    public MandrillMessagesApi getApi() {

        return mandrillApi.messages();
    }

}