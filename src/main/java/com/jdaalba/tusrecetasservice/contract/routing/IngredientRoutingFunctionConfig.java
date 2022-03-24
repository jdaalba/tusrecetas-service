package com.jdaalba.tusrecetasservice.contract.routing;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

import com.jdaalba.tusrecetasservice.contract.dto.Result;
import com.jdaalba.tusrecetasservice.domain.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IngredientRoutingFunctionConfig {

  private final IngredientService ingredientService;

  @Bean
  public RouterFunction<ServerResponse> getIngredients() {
    return route(
        GET("/ingredients"),
        request -> {
          log.info("Incoming request [{}]", request);
          return ok()
              .header("Access-Control-Allow-Origin", "*")
              .body(
                  new Result<>(
                      request.param("like")
                          .map(ingredientService::findAllLike)
                          .orElseGet(ingredientService::findAll)
                  )
              );
        }
    );
  }
}
