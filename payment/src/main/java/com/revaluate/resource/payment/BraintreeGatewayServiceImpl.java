package com.revaluate.resource.payment;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.revaluate.core.bootstrap.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class BraintreeGatewayServiceImpl implements BraintreeGatewayService {

    @Autowired
    private ConfigProperties configProperties;

    private BraintreeGateway braintreeGateway;

    @PostConstruct
    private void initialize() {
        this.braintreeGateway = new BraintreeGateway(
                configProperties.isProduction() ? Environment.PRODUCTION : Environment.SANDBOX,
                configProperties.getBraintreeMerchantId(),
                configProperties.getBraintreePublicKey(),
                configProperties.getBraintreePrivateKey()
        );
    }

    @Override
    @NotNull
    public BraintreeGateway getBraintreeGateway() {
        return braintreeGateway;
    }
}