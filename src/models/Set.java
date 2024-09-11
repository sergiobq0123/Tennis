package models;

import java.util.ArrayList;
import java.util.List;

public class Set {
    private int id;
    private int idMatch;
    private Player player1;
    private Player player2;
    private List<Game> games;

    public Set(int id, int idMatch, Player player1, Player player2) {
        this.id = id;
        this.idMatch = idMatch;
        this.player1 = player1;
        this.player2 = player2;
        this.games = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
