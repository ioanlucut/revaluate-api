package com.revaluate.dozer.converter;

import com.revaluate.color.persistence.Color;
import com.revaluate.color.persistence.ColorRepository;
import com.revaluate.domain.color.ColorDTO;
import com.revaluate.domain.color.ColorDTOBuilder;
import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ColorConverter extends DozerConverter<ColorDTO, Color> {

    @Autowired
    private ColorRepository colorRepository;

    public ColorConverter() {
        super(ColorDTO.class, Color.class);
    }

    public Color convertTo(ColorDTO source, Color destination) {
        Optional<Color> byColor = colorRepository.findOneById(source.getId());
        byColor.orElseThrow(() -> new IllegalArgumentException("The given color does not exists"));

        return byColor.get();
    }

    @Override
    public ColorDTO convertFrom(Color source, ColorDTO destination) {

        return new ColorDTOBuilder().withId(source.getId()).withColor(source.getColor()).build();
    }

}