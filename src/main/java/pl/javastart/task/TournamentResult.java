package pl.javastart.task;

public class TournamentResult {
    private final Player player;
    private final int result;

    public TournamentResult(Player player, int result) {
        this.player = player;
        this.result = result;
    }

    public Player getPlayer() {
        return player;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return player + " " + result;
    }

    public String outputData() {
        return player + ";" + result;
    }
}
