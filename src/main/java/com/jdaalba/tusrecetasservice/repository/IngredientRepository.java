package com.jdaalba.tusrecetasservice.repository;

import com.jdaalba.tusrecetasservice.entity.Ingredient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface IngredientRepository extends Neo4jRepository<Ingredient, Long> {

  Optional<Ingredient> findByName(String name);
  @Query("MATCH (ingredient:Ingredient)"
      + " WHERE ingredient.name =~ (('(?i)' + $name) + '.*') "
      + " RETURN ingredient{.id, .name, __nodeLabels__: labels(ingredient), "
      + "__internalNeo4jId__: id(ingredient)}")
  List<Ingredient> findAllByNameLike(String name);
}