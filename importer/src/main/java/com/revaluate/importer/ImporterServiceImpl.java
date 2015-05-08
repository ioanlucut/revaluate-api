package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.importer.profile.ExpenseColumn;
import com.revaluate.importer.profile.ExpenseProfile;
import com.univocity.parsers.common.processor.ObjectRowListProcessor;
import com.univocity.parsers.conversions.Conversions;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
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
    public void doParse() {

    }

    @Override
    public List<ExpenseDTO> importFrom(Reader reader, ExpenseProfile expenseProfile) {
        ObjectRowListProcessor rowProcessor = new ObjectRowListProcessor();

        //-----------------------------------------------------------------
        // Amount should be converted to double
        //-----------------------------------------------------------------
        rowProcessor.convertIndexes(Conversions.replace(NON_DIGIT, ""), Conversions.toDouble()).set(expenseProfile.getAmountExpenseProfileEntryDTO().getImportColumnIndex());

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator(LINE_SEPARATOR);
        parserSettings.getFormat().setDelimiter(expenseProfile.getDelimiter());
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(HEADER_EXTRACTION_ENABLED);

        //-----------------------------------------------------------------
        // Exclude any other row
        //-----------------------------------------------------------------
        parserSettings.selectIndexes(expenseProfile.getIndexes());

        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(reader);

        List<Object[]> rows = rowProcessor.getRows();

        return rows
                .stream()
                .map(objects -> new ExpenseDTOBuilder()
                        .withValue((Double) objects[expenseProfile.getIndexOf(ExpenseColumn.AMOUNT)])
                        .withDescription((String) objects[expenseProfile.getIndexOf(ExpenseColumn.DESCRIPTION)])
                        .build())
                .collect(Collectors.toList());
    }
}