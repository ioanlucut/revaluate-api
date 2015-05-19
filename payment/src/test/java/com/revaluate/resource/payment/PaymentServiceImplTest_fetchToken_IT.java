package com.revaluate.resource.payment;

import com.braintreegateway.Customer;
import com.braintreegateway.ResourceCollection;
import com.braintreegateway.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__payment__test.xml")
@ActiveProfiles("IT")
public class PaymentServiceImplTest_fetchToken_IT {

    private static final String IOAN_LUCUT_CUSTOMER_ID_SANDBOX = "61714209";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private PaymentService paymentService;

    @Test
    public void fetchToken_validCustomerId_isOk() throws Exception {
        String customerIdToken = paymentService.fetchToken(IOAN_LUCUT_CUSTOMER_ID_SANDBOX);

        assertThat(customerIdToken, is(notNullValue()));
    }

    @Test
    public void fetchCustomer_validCustomerId_isOk() throws Exception {
        Customer customer = paymentService.findCustomer(IOAN_LUCUT_CUSTOMER_ID_SANDBOX);

        assertThat(customer, is(notNullValue()));
    }

    @Test
    public void findTransactions_validCustomerId_isOk() throws Exception {
        ResourceCollection<Transaction> transactions = paymentService.findTransactions(IOAN_LUCUT_CUSTOMER_ID_SANDBOX);

        assertThat(transactions, is(notNullValue()));
    }

    @Test
    public void fetchToken_invalidCustomerId_exceptionThrown() throws Exception {
        exception.expect(ConstraintViolationException.class);
        paymentService.fetchToken(null);

        exception.expect(ConstraintViolationException.class);
        paymentService.fetchToken("");
    }
}