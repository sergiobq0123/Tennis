package models;

import java.sql.Date;

public class Match {

    private int id;
    private Date date;
    private int setsNumber;
    private int idPlayer1;
    private int idPlayer2;
    private Integer setsPlayer1;
    private Integer setsPlayer2;
    private Integer idMatchWinner;

    public Match(int id, int setsNumber, int idPlayer1, int idPlayer2) {
        this.id = id;
        this.date = new Date(System.currentTimeMillis());
        this.setsNumber = setsNumber;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
    }

    public Match() {
        this.date = new Date(System.currentTimeMillis());
    }

    public Match(int setsNumber, int idPlayer1, int idPlayer2) {
        this.date = new Date(System.currentTimeMillis());
        this.setsNumber = setsNumber;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSetsNumber() {
        return setsNumber;
    }

    public void setSetsNumber(int setsNumber) {
        this.setsNumber = setsNumber;
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

    public Integer getIdMatchWinner() {
        return idMatchWinner;
    }

    public void setIdMatchWinner(Integer idMatchWinner) {
        this.idMatchWinner = idMatchWinner;
    }
}
