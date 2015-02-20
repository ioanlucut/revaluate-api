package com.revaluate.account.service;

import com.revaluate.account.domain.LoginDTO;
import com.revaluate.account.domain.LoginDTOBuilder;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.persistence.UserEmailToken;
import com.revaluate.account.persistence.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTestIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserDTO userDTO;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        if (userDTO != null) {
            userService.remove(userDTO.getId());
        }
    }

    @Test
    public void testIsUnique() throws Exception {
        assertThat(userService.isUnique("abc@def.ghi"), is(true));
    }

    @Test
    public void testCreate() throws Exception {
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        assertThat(createdUserDTO, is(notNullValue()));
        assertThat(userDTO.getEmail(), equalTo(createdUserDTO.getEmail()));
        assertThat(userDTO.getFirstName(), equalTo(createdUserDTO.getFirstName()));
        assertThat(userDTO.getLastName(), equalTo(createdUserDTO.getLastName()));
        assertThat(userDTO.getPassword(), not(equalTo(createdUserDTO.getPassword())));
        assertThat(createdUserDTO.getId(), not(equalTo(0)));
    }

    @Test
    public void testLogin() throws Exception {
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        LoginDTO loginDTO = new LoginDTOBuilder().withEmail("xx@xx.xx").withPassword("1234567").build();

        UserDTO loggedUserDTO = userService.login(loginDTO);
        assertThat(loggedUserDTO, is(notNullValue()));
        assertThat(userDTO.getEmail(), equalTo(loggedUserDTO.getEmail()));
        assertThat(userDTO.getFirstName(), equalTo(loggedUserDTO.getFirstName()));
        assertThat(userDTO.getLastName(), equalTo(loggedUserDTO.getLastName()));
        assertThat(userDTO.getPassword(), not(equalTo(loggedUserDTO.getPassword())));
        assertThat(loggedUserDTO.getId(), not(equalTo(0)));
    }

    @Test
    public void testUpdate() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Update a user
        UserDTO userDTOToUpdate = new UserDTOBuilder().withEmail("xx@xx.xx2").withFirstName("fn2").withLastName("ln2").withPassword("12345672").build();
        UserDTO updatedUserDTO = userService.update(userDTOToUpdate, createdUserDTO.getId());

        assertThat(updatedUserDTO, is(notNullValue()));
        // email excluded
        assertThat(updatedUserDTO.getEmail(), not(equalTo(userDTOToUpdate.getEmail())));
        assertThat(updatedUserDTO.getFirstName(), equalTo(userDTOToUpdate.getFirstName()));
        assertThat(updatedUserDTO.getLastName(), equalTo(userDTOToUpdate.getLastName()));
        assertThat(updatedUserDTO.getId(), not(equalTo(0)));
        assertThat(updatedUserDTO.getId(), equalTo(userDTO.getId()));
        assertThat(updatedUserDTO.getPassword(), not(equalTo(userDTOToUpdate.getPassword())));
    }

    @Test
    public void testGetUserDetails() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        UserDTO userDetailsDTO = userService.getUserDetails(userDTO.getId());

        assertThat(userDetailsDTO, is(notNullValue()));
        // email excluded
        assertThat(userDetailsDTO.getEmail(), equalTo(userDTO.getEmail()));
        assertThat(userDetailsDTO.getFirstName(), equalTo(userDTO.getFirstName()));
        assertThat(userDetailsDTO.getLastName(), equalTo(userDTO.getLastName()));
        assertThat(userDetailsDTO.getId(), not(equalTo(0)));
        assertThat(userDetailsDTO.getId(), equalTo(userDTO.getId()));
        assertThat(userDetailsDTO.getPassword(), not(equalTo(userDTO.getPassword())));
    }

    @Test
    public void testRemove() throws Exception {
    }

    @Test
    public void testUpdatePassword() throws Exception {
    }

    @Test
    public void sendVerificationEmailToken() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        UserDTO userDTOWithVerificationEmailTokenSent = userService.requestSignUpRegistration(userDTO.getEmail());

        assertThat(userDTOWithVerificationEmailTokenSent, is(notNullValue()));
        // email excluded
        assertThat(userDTOWithVerificationEmailTokenSent.getEmail(), equalTo(userDTO.getEmail()));
        assertThat(userDTOWithVerificationEmailTokenSent.getFirstName(), equalTo(userDTO.getFirstName()));
        assertThat(userDTOWithVerificationEmailTokenSent.getLastName(), equalTo(userDTO.getLastName()));
        assertThat(userDTOWithVerificationEmailTokenSent.getId(), not(equalTo(0)));
        assertThat(userDTOWithVerificationEmailTokenSent.getId(), equalTo(userDTO.getId()));
        assertThat(userDTOWithVerificationEmailTokenSent.getPassword(), not(equalTo(userDTO.getPassword())));
    }

    @Test
    public void sendRequestEmailTokenWhileExistsOverrideTheFirstOne() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // First time
        userService.requestSignUpRegistration(userDTO.getEmail());
        UserEmailToken firstEmailToken = userRepository.findOne(userDTO.getId()).getEmailToken();

        // Second time
        userService.requestSignUpRegistration(userDTO.getEmail());
        UserEmailToken secondEmailToken = userRepository.findOne(userDTO.getId()).getEmailToken();

        assertThat(secondEmailToken.getToken(), not(equalTo(firstEmailToken.getToken())));
    }
}