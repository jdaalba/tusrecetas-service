package com.jdaalba.tusrecetasservice.infrastructure;

import com.jdaalba.tusrecetasservice.domain.Recipe;
import com.jdaalba.tusrecetasservice.domain.repository.IngredientRepository;
import com.jdaalba.tusrecetasservice.domain.repository.RecipeRepository;
import java.util.List;

public class IngredientRepositoryImpl implements IngredientRepository, RecipeRepository {

  private static final List<String> INGREDIENTS = List.of(
      "acelgas",
      "apio",
      "arroz",
      "bacalao",
      "cebolla",
      "coliflor",
      "espinacas",
      "garbanzos",
      "huevo",
      "patata",
      "pollo",
      "puerros",
      "zanahoria"
      );

  private static final List<Recipe> RECIPES = List.of(
    new Recipe("Bacalao a la dorada", List.of("huevo", "patata", "bacalao")),
    new Recipe("Tortilla de patata", List.of("huevo", "patata", "cebolla")),
    new Recipe("Tortilla francesa", List.of("huevo"))
  );

  @Override
  public List<String> findAll() {
    return INGREDIENTS;
  }

  @Override
  public List<String> findAllLike(String hint) {
    return INGREDIENTS.stream()
        .filter(i -> i.startsWith(hint))
        .toList();
  }

  @Override
  public List<Recipe> findAllWith(List<String> ingredients) {
    return RECIPES.stream()
        .filter(r -> r.ingredients().containsAll(ingredients))
        .toList();
  }
}
