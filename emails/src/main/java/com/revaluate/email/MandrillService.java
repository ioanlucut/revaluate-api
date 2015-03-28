package com.revaluate.email;

import com.microtripit.mandrillapp.lutung.controller.MandrillMessagesApi;

public interface MandrillService {

    MandrillMessagesApi getApi();
}