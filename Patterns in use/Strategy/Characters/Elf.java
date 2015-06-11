package HomeWork.HomeWork8.Task2.Strategy.Characters;

import HomeWork.HomeWork8.Task2.Strategy.Movements.Flying;
import HomeWork.HomeWork8.Task2.Strategy.Movements.Walking;

public class Elf extends Character {
    public Elf(String name, int strength, int health) {
        super(name, strength, health);
    }

    public void walk() {
        movement = new Walking();
        move();
    }

    public void useMagic() {
        movement = new Flying();
    }

    public void fly() {
        if (movement.getClass() == Flying.class) {
            move();
        } else {
            System.out.println(name + " can't fly, use magic before");
        }
    }
}
