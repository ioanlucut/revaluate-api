package com.revaluate.color.service;

import com.revaluate.domain.color.ColorDTO;

import java.util.List;

public interface ColorService {

    List<ColorDTO> findAllColors();
}