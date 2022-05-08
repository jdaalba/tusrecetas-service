package com.jdaalba.tusrecetasservice.routing;

import com.jdaalba.tusrecetasservice.dto.ResultDTO;
import com.jdaalba.tusrecetasservice.entity.Recipe;
import com.jdaalba.tusrecetasservice.repository.RecipeRepository;
import com.jdaalba.tusrecetasservice.service.RecipeService;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
      @RequestParam(value = "idList", required = false) String idList) {
    final List<Recipe> found;
    if (Objects.isNull(idList) || idList.isEmpty()) {
      found = recipeRepository.findAll();
    } else {
      final var ids = Stream.of(idList.split(",")).map(Long::parseLong).toList();
      final var rs = recipeRepository.findAllByIngredientsId(ids);
      found = recipeRepository.findAllById(rs);
    }

    return ResponseEntity.ok(new ResultDTO<>(found));
  }

  @PostMapping
  public ResponseEntity<Void> saveNew(@RequestBody Recipe recipe) {
    return ResponseEntity
        .created(URI.create("http://localhost:8080/recipes/" + recipeService.save(recipe).getId()))
        .build();
  }
}