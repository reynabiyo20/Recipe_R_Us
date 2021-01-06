package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Instruction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstructionRepository extends CrudRepository<Instruction, Integer> {
    List<Instruction> findByRecipeId(Integer recipeId);
}