package com.jdaalba.tusrecetasservice.routing;

import com.jdaalba.tusrecetasservice.dto.ResultDTO;
import com.jdaalba.tusrecetasservice.entity.Recipe;
import com.jdaalba.tusrecetasservice.repository.RecipeRepository;
import com.jdaalba.tusrecetasservice.service.RecipeService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest;

@Slf4j
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class RecipeController {

  private final RecipeService recipeService;

  private final RecipeRepository recipeRepository;

  @GetMapping
  public ResponseEntity<ResultDTO<Recipe>> getRecipes(
      @RequestParam(value = "ingredients", required = false) String ids) {
    final List<Recipe> found;
    if (Objects.isNull(ids) || ids.isEmpty()) {
      found = recipeRepository.findAll();
    } else {
      final var arr = ids.split(",");
      final var uuids = Stream.of(arr).map(Long::parseLong).toList();
      final var rs = recipeRepository.findAllByIngredientsId(uuids);
      found = rs.stream().map(recipeRepository::findById).map(Optional::get).toList();
    }

    return ResponseEntity.ok(new ResultDTO<>(found));
  }

  @PostMapping
  public ResponseEntity<Void> saveNew(ServerRequest request) throws ServletException, IOException {
    return ResponseEntity.created(
        request.uriBuilder()
            .path("/" + recipeService.save(request.body(Recipe.class)).getId())
            .build())
        .build();
  }
}