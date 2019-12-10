package com.fycedwin.chicken;

import java.util.ArrayList;
import java.util.List;

public class ChickenEndPoint {

    public class CallBack{

        List<Recipe> recipe = new ArrayList<Recipe>();

        public List<Recipe> getRecipe() {
            return recipe;
        }

        public class Recipe{

            private String name;
            private String creator;
            private String review;
            private String rating;
            private String image;
            private List<String>ingredients;

            public String getName() {
                return name;
            }

            public String getCreator() {
                return creator;
            }

            public String getReview() {
                return review;
            }

            public String getRating() {
                return rating;
            }

            public String getImage() {
                return image;
            }

            public List<String> getIngredients() {
                return ingredients;
            }


        }
    }

}
