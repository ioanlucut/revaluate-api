package com.revaluate;

import com.revaluate.account.persistence.User;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public final class CustomerTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerTransformer.class);

    private CustomerTransformer() {
    }

    @Converter
    public static User toCustomer(SendTo sendTo, Exchange exchange) throws Exception {
        return null;
    }

}
