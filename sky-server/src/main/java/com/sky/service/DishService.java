package com.sky.service;

import com.sky.dto.DishDTO;
import org.springframework.stereotype.Service;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);
}
