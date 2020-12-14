package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
   User findByUsername(String username);

   User getById(Integer id);
}
