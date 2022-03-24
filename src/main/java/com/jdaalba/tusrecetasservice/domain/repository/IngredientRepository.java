package com.jdaalba.tusrecetasservice.domain.repository;

import java.util.List;

public interface IngredientRepository {

  List<String> findAll();

  List<String> findAllLike(String hint);
}
