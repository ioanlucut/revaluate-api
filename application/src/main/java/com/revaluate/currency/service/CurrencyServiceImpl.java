package com.revaluate.currency.service;

import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.currency.persistence.Currency;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.domain.currency.CurrencyDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public boolean isUnique(String currencyCode) {
        return !currencyRepository.findOneByCurrencyCode(currencyCode).isPresent();
    }

    @Override
    public CurrencyDTO create(CurrencyDTO currencyDTO) throws CurrencyException {
        Optional<Currency> currencyByName = currencyRepository.findOneByCurrencyCode(currencyDTO.getCurrencyCode());
        if (currencyByName.isPresent()) {
            throw new CurrencyException("Currency name is not unique");
        }

        Currency currency = dozerBeanMapper.map(currencyDTO, Currency.class);
        Currency savedCurrency = currencyRepository.save(currency);
        return dozerBeanMapper.map(savedCurrency, CurrencyDTO.class);
    }

    @Override
    public List<CurrencyDTO> findAllCurrencies() {
        List<Currency> categories = currencyRepository.findAll();

        return categories
                .stream()
                .map(currency -> dozerBeanMapper.map(currency, CurrencyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(String currencyCode) throws CurrencyException {
        Optional<Currency> currencyById = currencyRepository.findOneByCurrencyCode(currencyCode);
        currencyById.orElseThrow(() -> new CurrencyException("The given currency does not exists"));

        currencyRepository.delete(currencyById.get());
    }
}