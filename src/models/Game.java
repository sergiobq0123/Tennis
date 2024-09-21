package models;

public class Game {
    private int id;
    private int idPlayer1;
    private int idPlayer2;
    private int idSet;
    private Integer idGameWinner;

    public Game() {

    }

    public Game(int idPlayer1, int idPlayer2, int idSet) {
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
        this.idSet = idSet;
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

    public int getIdSet() {
        return idSet;
    }

    public void setIdSet(int idSet) {
        this.idSet = idSet;
    }

    public Integer getIdGameWinner() {
        return idGameWinner;
    }

    public void setIdGameWinner(Integer idGameWinner) {
        this.idGameWinner = idGameWinner;
    }

}
