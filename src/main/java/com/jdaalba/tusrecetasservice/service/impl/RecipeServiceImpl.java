package com.jdaalba.tusrecetasservice.service.impl;

import com.jdaalba.tusrecetasservice.service.RecipeService;
import com.jdaalba.tusrecetasservice.entity.Ingredient;
import com.jdaalba.tusrecetasservice.entity.Recipe;
import com.jdaalba.tusrecetasservice.repository.IngredientRepository;
import com.jdaalba.tusrecetasservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

  private final IngredientRepository ingredientRepository;

  private final RecipeRepository recipeRepository;

  @Override
  public Recipe save(Recipe recipe) {
    final var ingredients = ingredientRepository
        .findAllById(recipe.getIngredients().stream().map(Ingredient::getId).toList());

    if (recipe.getIngredients().size() != ingredients.size()) {
      throw new RuntimeException("some ingredients were not found");
    }

    recipe.getIngredients().clear();
    recipe.getIngredients().addAll(ingredients);
    return recipeRepository.save(recipe);
  }
}