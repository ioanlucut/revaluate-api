package com.revaluate.dozer.converter;

import com.revaluate.currency.persistence.Currency;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrencyConverter extends DozerConverter<CurrencyDTO, Currency> {

    @Autowired
    private CurrencyRepository currencyRepository;

    public CurrencyConverter() {
        super(CurrencyDTO.class, Currency.class);
    }

    public Currency convertTo(CurrencyDTO source, Currency destination) {
        Optional<Currency> byCurrencyCode = currencyRepository.findOneByCurrencyCode(source.getCurrencyCode());
        byCurrencyCode.orElseThrow(() -> new IllegalStateException("The given currency does not exists"));

        return byCurrencyCode.get();
    }

    @Override
    public CurrencyDTO convertFrom(Currency source, CurrencyDTO destination) {

        return new CurrencyDTOBuilder().withCurrencyCode(source.getCurrencyCode()).build();
    }

}