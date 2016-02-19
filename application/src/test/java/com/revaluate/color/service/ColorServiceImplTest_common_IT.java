package com.revaluate.color.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.color.persistence.Color;
import com.revaluate.color.persistence.ColorRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ColorServiceImplTest_common_IT extends AbstractIntegrationTests {

    @Autowired
    private ColorRepository colorRepository;

    @Test
    public void findAll_validDetails_ok() throws Exception {
        List<Color> allExistingColors = colorRepository.findAll();

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(allExistingColors).isNotNull();
        assertThat(allExistingColors.size()).isEqualTo(3);
    }
}
