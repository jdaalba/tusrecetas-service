package com.jdaalba.tusrecetasservice.domain.service;

import java.util.List;

public interface IngredientService {

  List<String> findAll();
  List<String> findAllLike(String hint);
}