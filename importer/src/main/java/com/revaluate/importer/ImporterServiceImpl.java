package com.revaluate.importer;

import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.importer.column.ExpenseColumn;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;
import com.univocity.parsers.common.processor.ObjectRowListProcessor;
import com.univocity.parsers.conversions.Conversions;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImporterServiceImpl implements ImporterService {

    public static final String LINE_SEPARATOR = "\n";
    public static final boolean HEADER_EXTRACTION_ENABLED = true;
    public static final String NON_DIGIT = "[^\\d]";

    @Override
    public List<ExpenseDTO> importFrom(Reader reader, ExpenseProfileDTO expenseProfileDTO) {
        ObjectRowListProcessor rowProcessor = new ObjectRowListProcessor();

        //-----------------------------------------------------------------
        // Amount should be converted to double
        //-----------------------------------------------------------------
        rowProcessor.convertIndexes(Conversions.replace(NON_DIGIT, ""), Conversions.toDouble()).set(expenseProfileDTO.getAmountExpenseProfileEntryDTO().getImportColumnIndex());

        //-----------------------------------------------------------------
        // Set some settings
        //-----------------------------------------------------------------
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator(LINE_SEPARATOR);
        parserSettings.getFormat().setDelimiter(expenseProfileDTO.getDelimiter());
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(HEADER_EXTRACTION_ENABLED);

        //-----------------------------------------------------------------
        // Exclude any unwanted row
        //-----------------------------------------------------------------
        parserSettings.selectIndexes(expenseProfileDTO.getIndexes());

        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(reader);

        List<Object[]> rows = rowProcessor.getRows();

        //-----------------------------------------------------------------
        // We define a date time formatter
        //-----------------------------------------------------------------
        DateTimeFormatter dateTimeFormatter = expenseProfileDTO.getSpentDateFormat() != null ? DateTimeFormat.forPattern(expenseProfileDTO.getSpentDateFormat()) : ISODateTimeFormat.dateTimeNoMillis();

        return rows
                .stream()
                .map(objects -> new ExpenseDTOBuilder()
                        .withValue((Double) objects[expenseProfileDTO.getIndexOf(ExpenseColumn.AMOUNT)])
                        .withDescription((String) objects[expenseProfileDTO.getIndexOf(ExpenseColumn.DESCRIPTION)])
                        .withCategory(new CategoryDTOBuilder().withName((String) objects[expenseProfileDTO.getIndexOf(ExpenseColumn.CATEGORY)]).build())
                        .withSpentDate(dateTimeFormatter.withOffsetParsed().parseLocalDateTime(((String) objects[expenseProfileDTO.getIndexOf(ExpenseColumn.SPENT_DATE)])))
                        .build())
                .collect(Collectors.toList());
    }
}