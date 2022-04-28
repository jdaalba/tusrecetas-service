package com.jdaalba.tusrecetasservice.routing;

import static java.util.Objects.requireNonNull;
import static org.springframework.web.servlet.function.RequestPredicates.DELETE;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.created;
import static org.springframework.web.servlet.function.ServerResponse.notFound;
import static org.springframework.web.servlet.function.ServerResponse.ok;

import com.jdaalba.tusrecetasservice.dto.ResultDTO;
import com.jdaalba.tusrecetasservice.entity.Ingredient;
import com.jdaalba.tusrecetasservice.repository.IngredientRepository;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import javax.servlet.ServletException;
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
public class IngredientRoutingFunctionConfig {

  private final IngredientRepository repository;

  @Bean
  public RouterFunction<ServerResponse> getIngredient() {
    final var url = "/ingredients/{id}";
    return route(
        GET(url).or(DELETE(url)),
        request -> {
          log.info("Incoming request [{}]", request);
          return repository.findById(Long.parseLong(request.pathVariable("id")))
              .map(handleIdRequest(request))
              .orElse(notFound().build());
        }
    );
  }

  private Function<Ingredient, ServerResponse> handleIdRequest(ServerRequest request) {
    return switch (requireNonNull(request.method())) {
      case GET -> f -> ok().body(new ResultDTO<>(List.of(f)));
      case DELETE -> f -> {
        repository.delete(f);
        return ok().build();
      };
      default -> throw new UnsupportedOperationException();
    };
  }


  @Bean
  public RouterFunction<ServerResponse> getIngredients() {
    return route(
        path("/ingredients"),
        request -> {
          log.info("Incoming request [{}]", request);
          return switch (requireNonNull(request.method())) {
            case GET -> ok()
                .body(
                    new ResultDTO<>(
                        request.param("like")
                            .map(i -> repository.findAll())
                            .orElseGet(repository::findAll)
                    )
                );
            case POST -> saveNewIngredient(request);
            default -> throw new UnsupportedOperationException();
          };
        }
    );
  }


  public ServerResponse saveNewIngredient(ServerRequest request)
      throws ServletException, IOException {
    final var ingredient  = request.body(Ingredient.class);
    log.info("Incoming request [{}]", request);
    repository.findByName(ingredient.getName())
        .ifPresent(ing -> {
          throw new RuntimeException(String.format("%s already exists", ing));
        });
    final var save = repository.save(ingredient);
    return created(URI.create(request.uri() + "/" + save.getId().toString())).build();
  }
}
