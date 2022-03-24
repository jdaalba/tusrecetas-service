package com.jdaalba.tusrecetasservice.domain.service;

import com.jdaalba.tusrecetasservice.domain.Recipe;
import java.util.List;

public interface RecipeService {

  List<Recipe> findAllWith(List<String> ingredients);
}