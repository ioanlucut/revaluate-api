package com.revaluate.account.service;

import com.revaluate.domain.account.LoginDTO;
import com.revaluate.domain.account.ResetPasswordDTO;
import com.revaluate.domain.account.UpdatePasswordDTO;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.account.exception.UserException;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserService {

    boolean isUnique(@Email String email);

    @NotNull
    UserDTO create(@Valid UserDTO userDTO) throws UserException;

    @NotNull
    UserDTO login(@Valid LoginDTO loginDTO) throws UserException;

    @NotNull
    UserDTO update(@Valid UserDTO userDTO, int userId) throws UserException;

    @NotNull
    UserDTO getUserDetails(int userId) throws UserException;

    void remove(int userId);

    @NotNull
    UserDTO updatePassword(@Valid UpdatePasswordDTO updatePasswordDTO, int currentUserId) throws UserException;

    void requestResetPassword(@Email String email) throws UserException;

    void validateResetPasswordToken(@Email String email, String token) throws UserException;

    void resetPassword(@Valid ResetPasswordDTO resetPasswordDTO, String email, String token) throws UserException;
}