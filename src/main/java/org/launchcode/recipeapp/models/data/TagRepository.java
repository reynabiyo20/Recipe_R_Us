package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    }
