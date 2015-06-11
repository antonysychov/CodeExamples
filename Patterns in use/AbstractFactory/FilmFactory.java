package HomeWork.HomeWork8.Task2.AbstractFactory;

public class FilmFactory {
    static ConcreteFilmFactory getFactory(Language l) {
        switch (l) {
            case RUSSIAN:
                return new RussianFilmFactory();
            case ENGLISH:
                return new EnglishFilmFactory();
            case SPANISH:
                return new SpanishFilmFactory();
            default:
                return null;
        }
    }
}

abstract class ConcreteFilmFactory {
    abstract Film getFilm(String name);
}

class RussianFilmFactory extends ConcreteFilmFactory {
    RussianFilm getFilm(String name) {
        return new RussianFilm(name);
    }
}

class EnglishFilmFactory extends ConcreteFilmFactory {
    EnglishFilm getFilm(String name) {
        return new EnglishFilm(name);
    }
}

class SpanishFilmFactory extends ConcreteFilmFactory {
    SpanishFilm getFilm(String name) {
        return new SpanishFilm(name);
    }
}

abstract class Film {
    String name;
    Language language;

    public abstract void print();
}

class RussianFilm extends Film {
    public RussianFilm(String name) {
        this.name = name;
        this.language = Language.RUSSIAN;
    }

    @Override
    public void print() {
        System.out.println("Russian film");
    }
}

class EnglishFilm extends Film {
    public EnglishFilm(String name) {
        this.name = name;
        this.language = Language.ENGLISH;
    }

    @Override
    public void print() {
        System.out.println("English film");

    }
}

class SpanishFilm extends Film {

    public SpanishFilm(String name) {
        this.name = name;
        this.language = Language.SPANISH;
    }

    @Override
    public void print() {
        System.out.println("Spanish film");
    }
}





