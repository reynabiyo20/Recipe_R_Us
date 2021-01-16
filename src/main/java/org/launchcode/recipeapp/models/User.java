package org.launchcode.recipeapp.models;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oksana
 */
@Data
@Entity
public class User extends AbstractEntity {

   /* @updated AH*/

   private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


   @NotBlank(message = "UserName name is required")
   private String username;


   @NotBlank(message = "Password is required")
   private String pwHash;

   @Column(name = "user_role")
   private Role role;

   @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
   private List<UserRecipe> recipes = new ArrayList<>();


   public User() {
   }

   public User(String username, String password) {
      this.username = username;
      this.pwHash = encoder.encode(password);
   }

   public boolean isMatchingPassword(String password) {
      return encoder.matches(password, pwHash);

   }

}
