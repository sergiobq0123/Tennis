package models;

public class Set {
    private int id;
    private int idPlayer1;
    private int idPlayer2;
    private int idMatch;
    private Integer idSetWinner;

    public Set() {
    }

    public Set (int idMatch, int idPlayer1, int idPlayer2) {
        this.idMatch = idMatch;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
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

    public Integer getIdSetWinner() {
        return idSetWinner;
    }

    public void setIdSetWinner(Integer idSetWinner) {
        this.idSetWinner = idSetWinner;
    }
}