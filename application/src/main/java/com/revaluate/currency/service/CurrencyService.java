package com.revaluate.currency.service;

import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.currency.exception.CurrencyException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface CurrencyService {

    boolean isUnique(String currencyCode);

    @NotNull
    CurrencyDTO create(@Valid CurrencyDTO currencyDTO) throws CurrencyException;

    List<CurrencyDTO> findAllCurrencies();

    void remove(String currencyCode) throws CurrencyException;
}