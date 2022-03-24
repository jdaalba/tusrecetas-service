package com.jdaalba.tusrecetasservice.domain.service.impl;

import com.jdaalba.tusrecetasservice.domain.service.IngredientService;
import com.jdaalba.tusrecetasservice.domain.repository.IngredientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

  private final IngredientRepository ingredientRepository;

  @Override
  public List<String> findAll() {
    return ingredientRepository.findAll();
  }

  @Override
  public List<String> findAllLike(String hint) {
    return ingredientRepository.findAllLike(hint);
  }
}
