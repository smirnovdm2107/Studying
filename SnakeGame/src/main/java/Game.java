public class Game {
    private final Pole pole;
    private final Player player;
    public Game(Pole pole, Player player) {
        this.pole = pole;
        this.player = player;
    }
    public void play() {
        while (pole.getPosition().isValid()) {
            pole.makeMove(player.makeMove());
        }
    }

}
