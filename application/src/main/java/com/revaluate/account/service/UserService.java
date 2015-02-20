package com.revaluate.account.service;

import com.revaluate.account.domain.LoginDTO;
import com.revaluate.account.domain.UpdatePasswordDTO;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.exception.UserException;

public interface UserService<T> {

    boolean isUnique(String email);

    UserDTO create(UserDTO userDTO) throws UserException;

    UserDTO login(LoginDTO loginDTO) throws UserException;

    UserDTO update(UserDTO userDTO, int userId) throws UserException;

    UserDTO getUserDetails(int userId) throws UserException;

    void remove(int userId);

    UserDTO updatePassword(UpdatePasswordDTO updatePasswordDTO, int currentUserId) throws UserException;

    UserDTO requestSignUpRegistration(String email) throws UserException;
}