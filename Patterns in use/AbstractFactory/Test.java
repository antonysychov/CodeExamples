package HomeWork.HomeWork8.Task2.AbstractFactory;

public class Test {
    public static void main(String[] args) {
        ConcreteFilmFactory factory = FilmFactory.getFactory(Language.SPANISH);
        Film film = factory.getFilm("Batman");
        Player player = new Player();
        player.play(film);
    }
}

