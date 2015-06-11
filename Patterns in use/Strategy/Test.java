package HomeWork.HomeWork8.Task2.Strategy;

import HomeWork.HomeWork8.Task2.Strategy.Characters.Elf;
import HomeWork.HomeWork8.Task2.Strategy.Characters.Ork;

public class Test {

    public static void main(String[] args) {
        Ork ork = new Ork("Azog", 4, 4);
        ork.walk();

        Elf elf = new Elf("Orlando", 4, 4);
        elf.walk();
        elf.fly();
        elf.useMagic();
        elf.fly();
    }
}
