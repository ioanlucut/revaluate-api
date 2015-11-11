package com.revaluate.color.service;

import com.revaluate.color.persistence.Color;
import com.revaluate.color.persistence.ColorRepository;
import com.revaluate.domain.color.ColorDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public List<ColorDTO> findAllColors() {
        List<Color> colors = colorRepository.findAll();

        return colors
                .stream()
                .map(color -> dozerBeanMapper.map(color, ColorDTO.class))
                .collect(Collectors.toList());
    }
}