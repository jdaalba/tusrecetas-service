package com.jdaalba.tusrecetasservice.repository;

import com.jdaalba.tusrecetasservice.entity.Ingredient;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface IngredientRepository extends Neo4jRepository<Ingredient, UUID> {

  Optional<Ingredient> findByName(String name);
}