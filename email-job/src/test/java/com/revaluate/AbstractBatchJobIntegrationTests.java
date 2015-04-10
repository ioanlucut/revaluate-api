package com.revaluate;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailRepository;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.currency.service.CurrencyService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.CamelTestContextBootstrapper;
import org.joda.money.CurrencyUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration(locations = "classpath*:applicationContext__emailJob__test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "IT")
public class AbstractBatchJobIntegrationTests {

    public static final String TEST_EMAIL = "xx@xx.xx";
    public static final String TEST_PASSWORD = "1234567";
    public static final String TEST_NEW_PASSWORD = "9999999";
    public static final String TEST_PASSWORD_WRONG = "YYYYYYY";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected CurrencyService currencyService;

    @Autowired
    protected CurrencyRepository currencyRepository;

    @Autowired
    protected EmailRepository emailRepository;

    protected UserDTO userDTO;

    @Before
    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        currencyRepository.deleteAll();
    }

    //-----------------------------------------------------------------
    // Common methods
    //-----------------------------------------------------------------

    protected UserDTO createUserDTO(String email, String currencyCode) throws UserException {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(currencyCode).withDisplayName("").withNumericCode(0).build();
        try {
            currencyDTO = currencyService.create(currencyDTO);
        } catch (CurrencyException e) {
            throw new UserException((e));
        }
        return createUserDTO(email, currencyDTO);

    }

    public UserDTO createUserDTO(String email, CurrencyDTO currencyDTO) throws UserException {
        //-----------------------------------------------------------------
        // Compute the user
        //-----------------------------------------------------------------
        userDTO = new UserDTOBuilder().withEmail(email).withFirstName("fn").withLastName("ln").withPassword(TEST_PASSWORD).withCurrency(currencyDTO).build();
        UserDTO createdCategoryDTO = userService.create(userDTO);
        userDTO.setId(createdCategoryDTO.getId());

        return createdCategoryDTO;
    }

    protected UserDTO createUserDTO() throws UserException {
        return createUserDTO(TEST_EMAIL, CurrencyUnit.EUR.getCurrencyCode());
    }

    protected UserDTO createUserDTO(String email) throws UserException {
        return createUserDTO(email, CurrencyUnit.EUR.getCurrencyCode());
    }
}
