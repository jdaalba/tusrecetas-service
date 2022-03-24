package com.jdaalba.tusrecetasservice.boot.config;

import com.jdaalba.tusrecetasservice.domain.repository.IngredientRepository;
import com.jdaalba.tusrecetasservice.domain.repository.RecipeRepository;
import com.jdaalba.tusrecetasservice.domain.service.IngredientService;
import com.jdaalba.tusrecetasservice.domain.service.impl.IngredientServiceImpl;
import com.jdaalba.tusrecetasservice.infrastructure.IngredientRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public IngredientRepository ingredientRepository() {
    return new IngredientRepositoryImpl();
  }

  @Bean
  public IngredientService ingredientService(IngredientRepository ingredientRepository) {
    return new IngredientServiceImpl(ingredientRepository);
  }

  @Bean
  public RecipeRepository recipeRepository() {
    return new IngredientRepositoryImpl();
  }
}