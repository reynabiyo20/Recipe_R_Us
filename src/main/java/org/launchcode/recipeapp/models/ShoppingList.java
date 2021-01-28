package org.launchcode.recipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oksana
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
public class ShoppingList extends AbstractEntity {

   @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
   private List<RecipeShoppingList> recipes = new ArrayList<>();

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="user_id", nullable = false,
           foreignKey = @ForeignKey(name = "user_fk"))
   private User user;

   private Date date;

   private boolean active;

   public ShoppingList(Date date) {
      this.date = new Date();
   }

   public ShoppingList() {

   }
}
