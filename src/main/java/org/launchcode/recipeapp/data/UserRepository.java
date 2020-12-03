package org.launchcode.recipeapp.data;

import org.launchcode.recipeapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Oksana
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

   User findByUsername (String userName);
}
