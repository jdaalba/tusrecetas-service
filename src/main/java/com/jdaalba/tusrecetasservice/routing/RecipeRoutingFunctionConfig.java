package com.jdaalba.tusrecetasservice.routing;

import static java.util.Objects.requireNonNull;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.created;
import static org.springframework.web.servlet.function.ServerResponse.ok;

import com.jdaalba.tusrecetasservice.dto.ResultDTO;
import com.jdaalba.tusrecetasservice.entity.Recipe;
import com.jdaalba.tusrecetasservice.repository.RecipeRepository;
import com.jdaalba.tusrecetasservice.service.RecipeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Slf4j
@Configuration
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class RecipeRoutingFunctionConfig {

  private final RecipeService recipeService;

  private final RecipeRepository recipeRepository;

  @Bean
  public RouterFunction<ServerResponse> handleRecipesById() {
    return route(
        path("/recipes/{id}"),
        request -> {
          final Supplier<Optional<Recipe>> query = () -> recipeRepository.findById(Long.parseLong(request.pathVariable("id")));
          final Supplier<ServerResponse> notFound = () -> ServerResponse.notFound().build();
          return switch (requireNonNull(request.method())) {
            case GET -> query.get().map(ok()::body).orElseGet(notFound);
            case DELETE -> query.get()
                .map(r -> {
                  recipeRepository.delete(r);
                  return ok().build();
                })
                .orElseGet(notFound);
            default -> ServerResponse.badRequest().build();
          };
        }
    );
  }

  @Bean
  public RouterFunction<ServerResponse> handleRecipes() {
    return route(
        path("/recipes"),
        request ->
            switch (requireNonNull(request.method())) {
              case POST -> created(
                  request.uriBuilder()
                      .path("/" + recipeService.save(request.body(Recipe.class)).getId())
                      .build())
                  .build();
              case GET -> extracted(request);
              default -> throw new RuntimeException("method '" + request.method() + "' not valid");
            }
    );
  }

  private ServerResponse extracted(ServerRequest request) {
    final var ingredients = request.param("ingredients")
        .map(q -> q.split(","))
        .map(arr -> Stream.of(arr).map(UUID::fromString).toList())
        .map(recipeRepository::findAllByIngredientsId)
        .<List<Recipe>>map(ArrayList::new)
        .orElseGet(recipeRepository::findAll);
    return ok().body(new ResultDTO<>(ingredients));
  }
}
