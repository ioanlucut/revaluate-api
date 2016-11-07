package com.revaluate.currency.service;

import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.domain.currency.CurrencyDTO;
import net.bull.javamelody.MonitoredWithSpring;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@MonitoredWithSpring
public interface CurrencyService {

    boolean isUnique(String currencyCode);

    @NotNull
    CurrencyDTO create(@Valid CurrencyDTO currencyDTO) throws CurrencyException;

    List<CurrencyDTO> findAllCurrencies();

    void remove(String currencyCode) throws CurrencyException;
}