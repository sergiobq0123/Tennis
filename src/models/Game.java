package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private Player player1;
    private Player player2;
    private List<Point> points;
    private int pointsPlayer1;
    private int pointsPlayer2;
    private Player gameWinner;

    public Game(int id, Player player1, Player player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.points = new ArrayList<>();
        this.pointsPlayer1 = 0;
        this.pointsPlayer2 = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public void setPointsPlayer1(int pointsPlayer1) {
        this.pointsPlayer1 = pointsPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void setPointsPlayer2(int pointsPlayer2) {
        this.pointsPlayer2 = pointsPlayer2;
    }

    public Player getGameWinner() {
        return gameWinner;
    }

    public void setGameWinner(Player gameWinner) {
        this.gameWinner = gameWinner;
    }
}
