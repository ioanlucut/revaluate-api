package com.revaluate.account.service;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.UserPartialUpdateEnum;
import com.revaluate.domain.account.*;
import com.revaluate.groups.CreateUserGroup;
import com.revaluate.groups.CreateViaOauthUserGroup;
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
    @Validated(value = CreateViaOauthUserGroup.class)
    UserDTO createViaOauth(@Valid @NotNull UserDTO userDTO) throws UserException;

    @NotNull
    UserDTO login(@Valid @NotNull LoginDTO loginDTO) throws UserException;

    @NotNull
    UserDTO loginViaOauth(@Valid @Email @NotBlank String email, @NotNull UserType userType) throws UserException;

    @NotNull
    UserDTO update(@Valid @NotNull UserDTO userDTO, int userId, @NotNull UserPartialUpdateEnum userPartialUpdateEnum) throws UserException;

    @NotNull
    UserDTO getUserDetails(int userId) throws UserException;

    void remove(int userId) throws Exception;

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

    void sendFeedback(@Valid @NotNull FeedbackDTO feedbackDTO, int currentUserId) throws UserException;
}