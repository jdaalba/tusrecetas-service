package com.jdaalba.tusrecetasservice.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Relationship
  @Transient
  private List<Recipe> recipes;
}