package com.revaluate.payment;

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
                Environment.SANDBOX,
                "6748tp7gxxbj2cv8",
                "y5w9jtq7r35w6fy3",
                "6dc818e94271df9952fd7bf1bb80e90b"
        );
    }

    @Override
    @NotNull
    public BraintreeGateway getBraintreeGateway() {
        return braintreeGateway;
    }
}