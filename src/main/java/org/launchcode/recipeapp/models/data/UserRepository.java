package org.launchcode.recipeapp.models.data;


import org.launchcode.recipeapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
}