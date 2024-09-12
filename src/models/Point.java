package models;

public class Point {
    private int id;
    private int idPlayer1;
    private int idPlayer2;
    private String scorePlayer1;
    private String scorePlayer2;
    private int idPlayerService;
    private Integer idPointWinner;
    private int idGame;

    public Point() {
        this.scorePlayer1 = "0";
        this.scorePlayer2 = "0";
    }

    public Point(int id,int id_set, int id_game, int id_player1, int id_player2) {
        this.id = id;
        this.idPlayer1 = id_player1;
        this.idPlayer2 = id_player2;
        this.scorePlayer1 = "0";
        this.scorePlayer2 = "0";
        this.idGame = id_game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlayer1() {
        return idPlayer1;
    }

    public void setIdPlayer1(int idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public int getIdPlayer2() {
        return idPlayer2;
    }

    public void setIdPlayer2(int idPlayer2) {
        this.idPlayer2 = idPlayer2;
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

    public int getIdPlayerService() {
        return idPlayerService;
    }

    public void setIdPlayerService(int idPlayerService) {
        this.idPlayerService = idPlayerService;
    }

    public Integer getIdPointWinner() {
        return idPointWinner;
    }

    public void setIdPointWinner(Integer idPointWinner) {
        this.idPointWinner = idPointWinner;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", idPlayer1=" + idPlayer1 +
                ", idPlayer2=" + idPlayer2 +
                ", scorePlayer1='" + scorePlayer1 + '\'' +
                ", scorePlayer2='" + scorePlayer2 + '\'' +
                ", idPlayerService=" + idPlayerService +
                ", idPointWinner=" + idPointWinner +
                ", idGame=" + idGame +
                '}';
    }
}