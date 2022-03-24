package com.jdaalba.tusrecetasservice.domain.repository;

import com.jdaalba.tusrecetasservice.domain.Recipe;
import java.util.List;

public interface RecipeRepository {

  List<Recipe> findAllWith(List<String> ingredients);
}