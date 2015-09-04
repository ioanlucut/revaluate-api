package com.revaluate.config;

public interface AppConfig {

    int TRIAL_DAYS = 365;
    String VERSION = "1.0.0";

    int MIN_ALLOWED_CATEGORIES = 3;
    int MAX_ALLOWED_CATEGORIES = 20;

    int MIN_EXPENSES_TO_ENABLE_BULK_ACTION = 1;
    int IMPORT_MIN_CATEGORIES_TO_SELECT = 1;
    int SETUP_MIN_CATEGORIES_TO_SELECT = 3;

    int VALUE_INTEGER_SIZE = 20;
    int VALUE_FRACTION_SIZE = 2;

    int MIN_ALLOWED_GOALS = 0;
    int MAX_ALLOWED_GOALS = 3;
}
