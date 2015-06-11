package HomeWork.HomeWork8.Task2.Recipe;

public class Test {
    public static void main(String[] args) {

        Doctor doctor = new Doctor();
        Recipe recipe = doctor.createRecipe();
        System.out.println(recipe.getExpireDate());
        Recipe newRecipe = doctor.prolongRecipe(recipe, 15);
        System.out.println(newRecipe.getExpireDate());
    }
}
