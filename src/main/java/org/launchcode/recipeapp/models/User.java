package org.launchcode.recipeapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oksana
 */
@Entity
@Table
public class User extends AbstractEntity {

   @NotBlank(message = "User userName name is required")
   private String username;

   @NotBlank(message = "Email is required")
   @Email(message = "Invalid email. Try again.")
   private String email;

   @NotBlank(message = "Password is required")
   private String pwHash;

   @Column(name = "user_role")
   private Role role;

   @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
   private List<UserRecipe> recipes = new ArrayList<>();


   public User() {
   }

   public User(String username, String email, String pwHash) {
      this.username = username;
      this.email = email;
      this.pwHash = pwHash;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPwHash() {
      return pwHash;
   }

   public void setPwHash(String pwHash) {
      this.pwHash = pwHash;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public List<UserRecipe> getRecipes() {
      return recipes;
   }

   public void setRecipes(List<UserRecipe> recipes) {
      this.recipes = recipes;
   }

}
