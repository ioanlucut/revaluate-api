package com.revaluate.account.persistence;

import com.revaluate.account.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByEmail(String email);

    User findFirstByEmail(String email);

    User findFirstByEmailAndPassword(String email, String password);

    void deleteByEmail(String email);
}