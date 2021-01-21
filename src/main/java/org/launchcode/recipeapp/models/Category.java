package org.launchcode.recipeapp.models;

/**
 * @author Oksana
 */
public enum Category {

  BEVERAGES ("Beverages"),
  APPETIZER ("Appetizer"),
  ENTREE ("Entree"),
  SOUP ("Soup"),
  SIDES ("Sides"),
  DESSERT ("Dessert");

  private String name;

  Category(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}


