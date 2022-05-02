package com.jdaalba.tusrecetasservice.repository;

import com.jdaalba.tusrecetasservice.entity.Recipe;
import java.util.List;
import java.util.Set;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface RecipeRepository extends Neo4jRepository<Recipe, Long> {

  @Query("WITH $uuids as uuids"
      + " MATCH (i:Ingredient)"
      + " WHERE i.id IN uuids"
      + " WITH collect(i) AS ingredients"
      + " MATCH (r:Recipe)"
      + " WHERE ALL (i IN ingredients WHERE (i)<-[:MADE_OF]-(r))"
//      + " RETURN recipe{.id, .name, __nodeLabels__: labels(recipe), __internalNeo4jId__: id(recipe), Recipe_MADE_OF_Ingredient: [(recipe)-[:`MADE_OF`]->(recipe_ingredients:`Ingredient`) | recipe_ingredients{.id, .name, __nodeLabels__: labels(recipe_ingredients), __internalNeo4jId__: id(recipe_ingredients)}]}")
//      + " RETURN r, (r)-[:MADE_OF]->(:Ingredient)")
      + " RETURN r.id")
  Set<Long> findAllByIngredientsId(List<Long> ids);
}