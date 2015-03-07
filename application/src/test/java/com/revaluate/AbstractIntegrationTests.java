package com.revaluate;

import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import com.revaluate.currency.domain.CurrencyDTO;
import com.revaluate.currency.domain.CurrencyDTOBuilder;
import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.currency.service.CurrencyService;
import org.joda.money.CurrencyUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ActiveProfiles("IT")
public class AbstractIntegrationTests {

    public static final String FAKE_EMAIL = "xx@xx.xx";
    public static final String OLD_PASSWORD = "1234567";
    public static final String PASSWORD_WRONG = "YYYYYYY";
    public static final String NEW_PASSWORD = "9999999";

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected CurrencyService currencyService;

    @Autowired
    protected CurrencyRepository currencyRepository;

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
        userDTO = new UserDTOBuilder().withEmail(email).withFirstName("fn").withLastName("ln").withPassword("1234567").withCurrency(currencyDTO).build();
        UserDTO createdCategoryDTO = userService.create(userDTO);
        userDTO.setId(createdCategoryDTO.getId());

        return createdCategoryDTO;
    }

    protected UserDTO createUserDTO() throws com.revaluate.account.exception.UserException {
        return createUserDTO(FAKE_EMAIL, CurrencyUnit.EUR.getCurrencyCode());
    }

    protected UserDTO createUserDTO(String email) throws com.revaluate.account.exception.UserException {
        return createUserDTO(email, CurrencyUnit.EUR.getCurrencyCode());
    }

    protected List<EmailToken> getTokenOfType(User foundUser, EmailType emailType) {
        return foundUser.getEmailTokens().stream().filter(e -> e.getEmailType() == emailType).collect(Collectors.toList());
    }
}
