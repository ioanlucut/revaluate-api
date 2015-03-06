package com.revaluate.currency.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findOneByCurrencyCode(String currencyCode);
}