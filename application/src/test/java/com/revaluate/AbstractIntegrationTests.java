package com.revaluate;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.category.service.CategoryService;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.currency.service.CurrencyService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.color.ColorDTO;
import com.revaluate.domain.color.ColorDTOBuilder;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import com.revaluate.email.persistence.EmailRepository;
import com.revaluate.email.persistence.EmailTokenRepository;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.expense.service.ExpenseService;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import org.dozer.DozerBeanMapper;
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
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__application__test.xml")
@ActiveProfiles("IT")
public class AbstractIntegrationTests {

    public static final String TEST_EMAIL = "dev@revaluate.io";
    public static final String TEST_PASSWORD = "1234567";
    public static final String CUSTOMER_ID = "1234567";
    public static final String METHOD_PAYMENT_TOKEN = "ABCDEFGH";
    public static final String TOKEN_PAYMENT = "XXXXXX";
    public static final String TEST_NEW_PASSWORD = "9999999";
    public static final String TEST_PASSWORD_WRONG = "YYYYYYY";
    public static final String INVALID_TOKEN = "INVALID_TOKEN";
    public static final String VALID_COLOR = "#eee";
    public static final int VALID_COLOR_ID = 1;
    public static final String VALID_COLOR_2 = "#fff";
    public static final int VALID_COLOR_ID_2 = 2;
    public static final String VALID_COLOR_3 = "#fef";
    public static final int VALID_COLOR_ID_3 = 3;

    public static final ColorDTO FIRST_VALID_COLOR = new ColorDTOBuilder().withId(VALID_COLOR_ID).withColor(VALID_COLOR).build();
    public static final ColorDTO SECOND_VALID_COLOR = new ColorDTOBuilder().withId(VALID_COLOR_ID_2).withColor(VALID_COLOR_2).build();
    public static final ColorDTO THIRD_VALID_COLOR = new ColorDTOBuilder().withId(VALID_COLOR_ID_3).withColor(VALID_COLOR_3).build();

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
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ExpenseRepository expenseRepository;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected ExpenseService expenseService;

    @Autowired
    protected EmailTokenRepository emailTokenRepository;

    @Autowired
    protected EmailRepository emailRepository;

    @Autowired
    protected PaymentStatusRepository paymentStatusRepository;

    @Autowired
    protected DozerBeanMapper dozerBeanMapper;

    protected UserDTO userDTO;

    @BeforeClass
    public static void before() {
        System.setProperty(ConfigProperties.SPRING_PROFILE_ACTIVE, "it");
    }

    @Before
    @After
    public void tearDown() throws Exception {
        paymentStatusRepository.deleteAll();
        emailRepository.deleteAll();
        expenseRepository.deleteAll();
        categoryRepository.deleteAll();
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
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(currencyCode).withDisplayName("").withSymbol("$").withNumericCode(0).build();
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

    protected void setFieldViaReflection(Class<?> clazz, Object target, String name, Object value) {
        Field field = ReflectionUtils.findField(clazz, name);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, value);
    }
}
