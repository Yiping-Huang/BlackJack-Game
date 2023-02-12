package model;

public class Player {
    private String name;
    private int assets;
    private int rounds;

    public Player(String name, int assets, int rounds) {
        this.name = name;
        this.assets = assets;
        this.rounds = rounds;
    }

    public String getName() {
        return name;
    }

    public int getAssets() {
        return assets;
    }

    public int getRounds() {
        return rounds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssets(int assets) {
        this.assets = assets;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
