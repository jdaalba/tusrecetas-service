package com.jdaalba.tusrecetasservice.config;

import com.jdaalba.tusrecetasservice.service.RecipeService;
import com.jdaalba.tusrecetasservice.service.impl.RecipeServiceImpl;
import com.jdaalba.tusrecetasservice.repository.IngredientRepository;
import com.jdaalba.tusrecetasservice.repository.RecipeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public RecipeService recipeService(RecipeRepository recipeRepository,
      IngredientRepository ingredientRepository) {
    return new RecipeServiceImpl(ingredientRepository, recipeRepository);
  }
}