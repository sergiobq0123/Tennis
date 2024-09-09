package models;

import java.time.Instant;
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
}
