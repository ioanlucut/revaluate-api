package com.revaluate.core.converters;

import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Component
@Provider
public class DateTimeParamConverterProvider implements ParamConverterProvider {

    @Override
    public ParamConverter<LocalDateTime> getConverter(Class rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDateTime.class)) {
            return new DateTimeParamConverter();
        } else {
            return null;
        }
    }

    private static class DateTimeParamConverter implements ParamConverter<LocalDateTime> {

        @Override
        public LocalDateTime fromString(String value) {
            try {
                return ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime(value);
            } catch (IllegalArgumentException ex) {

                return ISODateTimeFormat.dateTime().parseLocalDateTime(value);
            }
        }

        @Override
        public String toString(LocalDateTime value) {
            return value.toString();
        }

    }
}