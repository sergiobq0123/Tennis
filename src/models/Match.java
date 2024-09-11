package models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Match {

    private int id;
    private Instant date;
    private int setsNumber;
    private Player player1;
    private Player player2;
    private List<Set> sets;

    public Match(int id, Instant date, int setsNumber, Player player1, Player player2) {
        this.id = id;
        this.date = date;
        this.setsNumber = setsNumber;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Match(int id, Instant date, int setsNumber, Player player1, Player player2, List<Set> sets) {
        this.id = id;
        this.date = date;
        this.setsNumber = setsNumber;
        this.player1 = player1;
        this.player2 = player2;
        this.sets = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public int getSetsNumber() {
        return setsNumber;
    }

    public void setSetsNumber(int setsNumber) {
        this.setsNumber = setsNumber;
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

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
}
