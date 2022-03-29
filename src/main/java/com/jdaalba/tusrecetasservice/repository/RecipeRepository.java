package com.jdaalba.tusrecetasservice.repository;

import com.jdaalba.tusrecetasservice.entity.Recipe;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface RecipeRepository extends Neo4jRepository<Recipe, UUID> {

  @Query("WITH $uuids as uuids"
      + " MATCH (i: Ingredient)"
      + " WHERE i.id IN uuids"
      + " WITH collect(i) AS ingredients"
      + " MATCH (r:Recipe)"
      + " WHERE ALL (i IN ingredients WHERE (i)<-[:MADE_OF]-(r))"
      + " RETURN r")
  Set<Recipe> findAllByIngredientsId(List<UUID> uuids);
}