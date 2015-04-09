package com.revaluate;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailRepository;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.currency.service.CurrencyService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import org.joda.money.CurrencyUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__application__test.xml")
@ActiveProfiles("IT")
public class AbstractIntegrationTests {

    public static final String TEST_EMAIL = "dev@revaluate.io";
    public static final String TEST_PASSWORD = "1234567";
    public static final String TEST_NEW_PASSWORD = "9999999";
    public static final String TEST_PASSWORD_WRONG = "YYYYYYY";
    public static final String INVALID_TOKEN = "INVALID_TOKEN";

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
    protected ConfigProperties configProperties;

    @Autowired
    protected EmailRepository emailRepository;

    protected UserDTO userDTO;

    @BeforeClass
    public static void before() {
        System.setProperty(ConfigProperties.SPRING_PROFILE_ACTIVE, "it");
    }

    @Before
    @After
    public void tearDown() throws Exception {
        emailRepository.deleteAll();
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
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(currencyCode).build();
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

    protected UserDTO createUserDTO() throws com.revaluate.account.exception.UserException {
        return createUserDTO(TEST_EMAIL, CurrencyUnit.EUR.getCurrencyCode());
    }

    protected UserDTO createUserDTO(String email) throws com.revaluate.account.exception.UserException {
        return createUserDTO(email, CurrencyUnit.EUR.getCurrencyCode());
    }
}
