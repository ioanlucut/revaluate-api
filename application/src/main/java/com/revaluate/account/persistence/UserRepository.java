package com.revaluate.account.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByEmail(String email);

    User findFirstByEmail(String email);
}