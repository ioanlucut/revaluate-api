package com.revaluate;

import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
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
    public static final String NEW_PASSWORD = "9999999";

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    protected UserDTO userDTO;

    @Before
    @After
    public void tearDown() throws Exception {
        if (userDTO != null) {
            if (userRepository.exists(userDTO.getId())) {
                userRepository.delete(userDTO.getId());
            }
        }
    }

    //-----------------------------------------------------------------
    // Common methods
    //-----------------------------------------------------------------

    protected UserDTO createUserDTO(String email) throws com.revaluate.account.exception.UserException {
        userDTO = new UserDTOBuilder().withEmail(email).withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdCategoryDTO = userService.create(userDTO);
        userDTO.setId(createdCategoryDTO.getId());

        return createdCategoryDTO;
    }

    protected UserDTO createUserDTO() throws com.revaluate.account.exception.UserException {
        return createUserDTO(FAKE_EMAIL);
    }

    protected List<EmailToken> getTokenOfType(User foundUser, EmailType emailType) {
        return foundUser.getEmailTokens().stream().filter(e -> e.getEmailType() == emailType).collect(Collectors.toList());
    }
}
