package com.revaluate.account.service;

import com.revaluate.account.domain.LoginDTO;
import com.revaluate.account.domain.UpdatePasswordDTO;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.exception.UserNotFoundException;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.core.jwt.JwtService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected JwtService jwtService;

    public boolean isUnique(String email) {
        LOGGER.info("Checking if email is unique - {email}", email);

        return userRepository.findByEmail(email).isEmpty();
    }

    public UserDTO create(UserDTO userDTO) throws UserException {
        LOGGER.info("Create user - {userDomain}", userDTO);

        if (!userRepository.findByEmail(userDTO.getEmail()).isEmpty()) {
            throw new UserException("Email is not unique");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        // Hash the password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        User savedUser = userRepository.save(user);
        if (savedUser != null) {
            UserDTO savedUserDTO = new UserDTO();
            BeanUtils.copyProperties(savedUser, savedUserDTO);

            return savedUserDTO;
        }

        throw new UserException("Error");
    }

    public UserDTO login(LoginDTO loginDTO) throws UserException {
        User foundUser = userRepository.findFirstByEmail(loginDTO.getEmail());
        if (foundUser == null) {
            throw new UserException("Invalid email or password");
        }

        if (!BCrypt.checkpw(loginDTO.getPassword(), foundUser.getPassword())) {
            throw new UserException("Invalid email or password");
        }

        UserDTO foundUserDTO = new UserDTO();
        BeanUtils.copyProperties(foundUser, foundUserDTO);

        return foundUserDTO;
    }

    public UserDTO update(UserDTO userDTO, int userId) throws UserException {
        User foundUser = userRepository.findOne(userId);
        if (foundUser == null) {
            throw new UserNotFoundException();
        }

        // Copy only selective properties
        BeanUtils.copyProperties(userDTO, foundUser, "id", "email", "password");

        User updatedUser = userRepository.save(foundUser);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = new UserDTO();
            BeanUtils.copyProperties(updatedUser, updatedUserDTO);
            return updatedUserDTO;
        }

        throw new UserException("Error");
    }

    public UserDTO getUserDetails(int userId) throws UserException {
        User foundUser = userRepository.findOne(userId);

        if (foundUser != null) {
            UserDTO foundUserDTO = new UserDTO();
            BeanUtils.copyProperties(foundUser, foundUserDTO);

            return foundUserDTO;
        }

        throw new UserException("Error");
    }

    public void remove(int userId) {
        userRepository.delete(userId);
    }

    public UserDTO updatePassword(UpdatePasswordDTO updatePasswordDTO, int currentUserId) throws UserException {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirmation())) {
            throw new UserException("New password should match new password confirmation");
        }

        User existingUser = userRepository.findOne(currentUserId);
        if (existingUser == null) {
            throw new UserNotFoundException("Invalid email or password");
        }

        if (!BCrypt.checkpw(updatePasswordDTO.getOldPassword(), existingUser.getPassword())) {
            throw new UserNotFoundException("Current password is wrong");
        }

        existingUser.setPassword(BCrypt.hashpw(updatePasswordDTO.getNewPassword(), BCrypt.gensalt()));

        User updatedUser = userRepository.save(existingUser);
        if (updatedUser != null) {

            UserDTO updatedUserDTO = new UserDTO();
            BeanUtils.copyProperties(updatedUser, updatedUserDTO);

            return updatedUserDTO;
        }

        throw new UserException("Error while trying to update the password.");
    }
}