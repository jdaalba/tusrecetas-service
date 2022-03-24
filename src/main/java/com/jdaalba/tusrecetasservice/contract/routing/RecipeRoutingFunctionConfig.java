package com.jdaalba.tusrecetasservice.contract.routing;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

import com.jdaalba.tusrecetasservice.contract.dto.Result;
import com.jdaalba.tusrecetasservice.domain.repository.RecipeRepository;
import com.jdaalba.tusrecetasservice.domain.service.IngredientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RecipeRoutingFunctionConfig {

  private final RecipeRepository recipeRepository;

  @Bean
  public RouterFunction<ServerResponse> getRecipes() {
    return route(
        GET("/recipes"),
        request -> {
          log.info("Incoming request [{}]", request);
          final var ingredients = request.param("ingredients")
              .map(s -> s.split(","))
              .map(List::of)
              .orElse(List.of());
          return ok()
              .header("Access-Control-Allow-Origin", "*")
              .body(
                  new Result<>(recipeRepository.findAllWith(ingredients))
              );
        }
    );
  }
}
