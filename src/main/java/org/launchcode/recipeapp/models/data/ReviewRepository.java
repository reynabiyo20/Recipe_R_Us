package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

}
