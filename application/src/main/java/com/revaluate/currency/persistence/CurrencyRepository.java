package com.revaluate.currency.persistence;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    String CURRENCIES_CACHE = "currencies";

    @Cacheable(CURRENCIES_CACHE)
    Optional<Currency> findOneByCurrencyCode(String currencyCode);

    @Override
    List<Currency> findAll();
}