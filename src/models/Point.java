package models;

public class Point {
    private int id;
    private Player player1;
    private Player player2;
    private String scorePlayer1;
    private String scorePlayer2;
    private Player playerService;
    private Player pointWinner;

    public Point() {
        this.scorePlayer1 = "0";
        this.scorePlayer2 = "0";
    }

    public Point(int id, Player player1, Player player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.scorePlayer1 = "0";
        this.scorePlayer2 = "0";
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

    public String getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(String scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public String getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(String scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Player getPlayerService() {
        return playerService;
    }

    public void setPlayerService(Player playerService) {
        this.playerService = playerService;
    }

    public Player getPointWinner() {
        return pointWinner;
    }

    public void setPointWinner(Player pointWinner) {
        this.pointWinner = pointWinner;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", scorePlayer1='" + scorePlayer1 + '\'' +
                ", scorePlayer2='" + scorePlayer2 + '\'' +
                ", playerService=" + playerService +
                ", playerWinner=" + pointWinner +
                '}';
    }
}