package com.revaluate.importer;

import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.importer.column.ExpenseColumn;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;
import com.univocity.parsers.common.processor.ObjectRowListProcessor;
import com.univocity.parsers.conversions.Conversions;
import com.univocity.parsers.conversions.DoubleConversion;
import com.univocity.parsers.conversions.FormattedBigDecimalConversion;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.Reader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Validated
public class ImporterParserServiceImpl implements ImporterParserService {

    public static final String LINE_SEPARATOR = "\n";
    public static final double AMOUNT_FALLBACK_AMOUNT = 0.0;

    @Override
    public List<ExpenseDTO> parseFrom(Reader reader, ExpenseProfileDTO expenseProfileDTO) throws ImporterException {
        ObjectRowListProcessor objectRowListProcessor = new ObjectRowListProcessor();

        //-----------------------------------------------------------------
        // Amount should be converted to double
        //-----------------------------------------------------------------
        DoubleConversion doubleConversion = Conversions.toDouble();
        doubleConversion.setValueIfStringIsNull(AMOUNT_FALLBACK_AMOUNT);

        objectRowListProcessor
                .convertFields(new FormattedBigDecimalConversion("0.00"))
                .set(expenseProfileDTO.getExpenseColumnMatchingMap().get(ExpenseColumn.AMOUNT));

        //-----------------------------------------------------------------
        // Set some settings
        //-----------------------------------------------------------------
        CsvParserSettings parserSettings = buildCsvParserSettings(expenseProfileDTO);
        parserSettings.setRowProcessor(objectRowListProcessor);

        //-----------------------------------------------------------------
        // Exclude any unwanted row
        //-----------------------------------------------------------------
        String[] selectedFields = expenseProfileDTO.getFields();
        parserSettings.selectFields(selectedFields);

        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(reader);
        } catch (Exception ex) {

            throw new ImporterException("The csv is not valid", ex);
        }

        List<Object[]> rows = objectRowListProcessor.getRows();

        //-----------------------------------------------------------------
        // We define a date time formatter
        //-----------------------------------------------------------------
        DateTimeFormatter dateTimeFormatter = expenseProfileDTO.getSpentDateFormat() != null ? DateTimeFormat.forPattern(expenseProfileDTO.getSpentDateFormat()) : ISODateTimeFormat.dateTimeNoMillis();

        //-----------------------------------------------------------------
        // Lambda function which takes care of the transformation
        //-----------------------------------------------------------------
        Function<Object[], ExpenseDTO> expenseDTOMapFunction = getExpenseDTOFunction(expenseProfileDTO, selectedFields, dateTimeFormatter);

        try {
            return rows
                    .stream()
                    .map(expenseDTOMapFunction)
                    .collect(Collectors.toList());
        } catch (ImporterWrapperException ex) {

            throw new ImporterException(ex.getCause());
        }
    }

    private CsvParserSettings buildCsvParserSettings(ExpenseProfileDTO expenseProfileDTO) {
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator(LINE_SEPARATOR);
        parserSettings.getFormat().setDelimiter(expenseProfileDTO.getDelimiter());
        parserSettings.setHeaderExtractionEnabled(Boolean.TRUE);
        parserSettings.setLineSeparatorDetectionEnabled(Boolean.TRUE);

        return parserSettings;
    }

    private Function<Object[], ExpenseDTO> getExpenseDTOFunction(ExpenseProfileDTO expenseProfileDTO, String[] selectedFields, DateTimeFormatter dateTimeFormatter) {
        return objects -> {
            try {
                List<String> selectedFieldsAsList = Arrays.asList(selectedFields);
                BigDecimal value = (BigDecimal) objects[selectedFieldsAsList.indexOf(expenseProfileDTO.getIndexOf(ExpenseColumn.AMOUNT))];
                value = value.abs();

                return new ExpenseDTOBuilder()
                        .withValue(value.setScale(2, BigDecimal.ROUND_DOWN).doubleValue())
                        .withDescription((String) objects[selectedFieldsAsList.indexOf(expenseProfileDTO.getIndexOf(ExpenseColumn.DESCRIPTION))])
                        .withCategory(new CategoryDTOBuilder().withName((String) objects[selectedFieldsAsList.indexOf(expenseProfileDTO.getIndexOf(ExpenseColumn.CATEGORY))]).build())
                        .withSpentDate(dateTimeFormatter.withOffsetParsed().parseLocalDateTime(((String) objects[selectedFieldsAsList.indexOf(expenseProfileDTO.getIndexOf(ExpenseColumn.SPENT_DATE))])))
                        .build();
            } catch (Exception ex) {

                throw new ImporterWrapperException(ex);
            }
        };
    }
}