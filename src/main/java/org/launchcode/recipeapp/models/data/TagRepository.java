package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByName (String name);
    List<Tag> findAll();

    }
