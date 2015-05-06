package com.revaluate.account.service;

import com.revaluate.account.exception.UserException;
import com.revaluate.domain.account.LoginDTO;
import com.revaluate.domain.account.ResetPasswordDTO;
import com.revaluate.domain.account.UpdatePasswordDTO;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.groups.CreateUserGroup;
import com.revaluate.groups.UpdateUserCurrencyGroup;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserService {

    boolean isUnique(@Email String email);

    @NotNull
    @Validated(value = CreateUserGroup.class)
    UserDTO create(@Valid @NotNull UserDTO userDTO) throws UserException;

    @NotNull
    UserDTO login(@Valid @NotNull LoginDTO loginDTO) throws UserException;

    @NotNull
    UserDTO update(@Valid @NotNull UserDTO userDTO, int userId) throws UserException;

    @NotNull
    UserDTO getUserDetails(int userId) throws UserException;

    void remove(int userId);

    void validateConfirmationEmailToken(@Email String email, @NotBlank String token) throws UserException;

    void requestConfirmationEmail(@Email String email) throws UserException;

    @NotNull
    UserDTO updatePassword(@Valid @NotNull UpdatePasswordDTO updatePasswordDTO, int currentUserId) throws UserException;

    void validateResetPasswordToken(@Email String email, @NotBlank String token) throws UserException;

    void resetPassword(@Valid @NotNull ResetPasswordDTO resetPasswordDTO, @Email String email, @NotBlank String token) throws UserException;

    void requestResetPassword(@Email String email) throws UserException;

    @NotNull
    @Validated(value = UpdateUserCurrencyGroup.class)
    UserDTO updateCurrency(@Valid @NotNull UserDTO userDTO, int userId) throws UserException;
}