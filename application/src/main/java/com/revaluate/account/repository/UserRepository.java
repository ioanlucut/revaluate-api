package com.revaluate.account.repository;

import com.revaluate.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByEmail(String email);

    void deleteByEmail(String email);
}