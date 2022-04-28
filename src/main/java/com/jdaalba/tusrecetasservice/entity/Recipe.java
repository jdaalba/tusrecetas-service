package com.jdaalba.tusrecetasservice.entity;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Node
@Data
public class Recipe {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Relationship(type ="MADE_OF", direction = Direction.OUTGOING)
  private Set<Ingredient> ingredients = new HashSet<>();
}