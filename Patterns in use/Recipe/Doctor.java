package HomeWork.HomeWork8.Task2.Recipe;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Doctor {
    private String name;
    private Specialization specialization;
    private boolean licence = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Recipe createRecipe() {
        return new Recipe(new Date(), this, new Date(System.currentTimeMillis()), new Patient(), 12, 12);
    }

    public Recipe prolongRecipe(Recipe recipe, int days) {
        if (recipe.getDoctor() == this) {
            try {
                Recipe newRecipe = (Recipe) recipe.clone();
                newRecipe.setExpireDate(this, new Date(recipe.getExpireDate().getTime() + TimeUnit.DAYS.toMillis(days)));
                return newRecipe;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("This doctor isn't allowed to prolong this recipe");
        }
        return null;
    }
}
