package com.revaluate.account.service;

import com.revaluate.account.domain.LoginDTO;
import com.revaluate.account.domain.UpdatePasswordDTO;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.exception.UserNotFoundException;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserEmailToken;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.core.jwt.JwtService;
import com.revaluate.core.token.TokenGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected JwtService jwtService;

    @Override
    public boolean isUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public UserDTO create(UserDTO userDTO) throws UserException {
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
            BeanUtils.copyProperties(savedUser, savedUserDTO, "password");

            return savedUserDTO;
        }

        throw new UserException("Could not create the user.");
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws UserException {
        User foundUser = userRepository.findFirstByEmail(loginDTO.getEmail());
        if (foundUser == null) {
            throw new UserException("Invalid email or password");
        }

        if (!BCrypt.checkpw(loginDTO.getPassword(), foundUser.getPassword())) {
            throw new UserException("Invalid email or password");
        }

        UserDTO foundUserDTO = new UserDTO();
        BeanUtils.copyProperties(foundUser, foundUserDTO, "password");

        return foundUserDTO;
    }

    @Override
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

        throw new UserException("Could not update the user.");
    }

    @Override
    public UserDTO getUserDetails(int userId) throws UserException {
        User foundUser = userRepository.findOne(userId);

        if (foundUser != null) {
            UserDTO foundUserDTO = new UserDTO();
            BeanUtils.copyProperties(foundUser, foundUserDTO);

            return foundUserDTO;
        }

        throw new UserException("Could not retrieve user details.");
    }

    @Override
    public void remove(int userId) {
        userRepository.delete(userId);
    }

    @Override
    public UserDTO updatePassword(UpdatePasswordDTO updatePasswordDTO, int currentUserId) throws UserException {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirmation())) {
            throw new UserException("New password should match new password confirmation");
        }

        User existingUser = userRepository.findOne(currentUserId);
        if (existingUser == null) {
            throw new UserException("Invalid email or password");
        }

        if (!BCrypt.checkpw(updatePasswordDTO.getOldPassword(), existingUser.getPassword())) {
            throw new UserException("Current password is wrong");
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

    @Override
    public void requestResetPassword(String email) throws UserException {
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UserException("No matching of this email");
        }
        UserEmailToken resetEmailToken = new UserEmailToken();
        resetEmailToken.setToken(TokenGenerator.getGeneratedToken());
        user.setResetEmailToken(resetEmailToken);

        User updatedUser = userRepository.save(user);

        if (updatedUser == null) {
            throw new UserException("Error while trying to reset password.");
        }
    }

    @Override
    public void validateResetPasswordToken(String email, String token) throws UserException {
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UserException("No matching of this email");
        }

        if (user.getResetEmailToken() == null) {
            throw new UserException("Token is invalid.");
        }

        if (!user.getResetEmailToken().getToken().equals(token)) {

            // Invalidate current token
            user.setResetEmailToken(null);
            userRepository.save(user);

            throw new UserException("Token is invalid.");
        }
    }
}