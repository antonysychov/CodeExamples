package HomeWork.HomeWork8.Task2.Strategy.Characters;

import HomeWork.HomeWork8.Task2.Strategy.Movements.Walking;

public class Ork extends Character {
    {
        movement = new Walking();
    }

    public Ork(String name, int strength, int health) {
        super(name, strength, health);
    }

    public void walk() {
        move();
    }
}
