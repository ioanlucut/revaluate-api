package com.revaluate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import io.github.fallwizard.configuration.FallwizardConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RevaluateConfiguration extends FallwizardConfiguration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
