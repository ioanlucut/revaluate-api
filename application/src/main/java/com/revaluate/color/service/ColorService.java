package com.revaluate.color.service;

import com.revaluate.domain.color.ColorDTO;
import net.bull.javamelody.MonitoredWithSpring;

import java.util.List;

@MonitoredWithSpring
public interface ColorService {

    List<ColorDTO> findAllColors();
}