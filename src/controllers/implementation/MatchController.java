package controllers.implementation;

import controllers.interfaces.IMatchController;

import dao.MatchDAO;
import dao.SetDao;
import models.Game;
import models.Match;
import models.Player;
import models.Set;

import java.sql.SQLException;

public class MatchController implements IMatchController {

    private int sumSetsPlayer1;
    private int sumSetsPlayer2;

    Match match;
    MatchDAO matchDAO = new MatchDAO();
    SetController setController;
    Player player1;
    Player player2;
    int setsNumber;

    @Override
    public void createMatch(int setsNumber, Player player1, Player player2) throws SQLException {
        initializeValues(setsNumber,player1, player2);
        simulateMatch();
    }

    private void initializeValues(int setsNumber, Player player1, Player player2) throws SQLException {
        this.player1 = player1;
        this.player2 = player2;
        this.setsNumber = setsNumber;

        match = new Match();
        match.setSetsNumber(setsNumber);
        match.setIdPlayer1(player1.getId());
        match.setIdPlayer2(player2.getId());
        matchDAO.addMatch(match);
    }

    private void simulateMatch() throws SQLException {
        while (match.getIdMatchWinner() == null) {
            setController = new SetController();
            Set set = setController.createSet(match, player1, player2);
            if (set.getIdSetWinner() == set.getIdPlayer1()) {
                sumSetsPlayer1++;
            } else {
                sumSetsPlayer2++;
            }
            verifyWinner(set.getIdSetWinner());
        }
    }

    private void verifyWinner(int idGameWinner) throws SQLException {
        boolean player1HasWon = sumSetsPlayer1 >= Math.ceil((double) setsNumber / 2);
        boolean player2HasWon = sumSetsPlayer2 >= Math.ceil((double) setsNumber / 2);
        if (player1HasWon) {
            updateMatch(player1.getId());
        } else if (player2HasWon) {
            updateMatch(player2.getId());
        }
    }
    private void updateMatch(int idWinner) throws SQLException {
        match.setIdMatchWinner(idWinner);
        match.setSetsPlayer1(sumSetsPlayer1);
        match.setSetsPlayer2(sumSetsPlayer2);
        matchDAO.updateSet(match);
    }

}

