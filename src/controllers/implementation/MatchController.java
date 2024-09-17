package controllers.implementation;

import controllers.interfaces.IMatchController;
import controllers.interfaces.ISetController;
import dao.MatchDAO;
import dao.SetDao;
import models.Match;
import models.Player;

import java.sql.SQLException;

public class MatchController implements IMatchController {

    private Match match;
    private final MatchDAO matchDAO;
    private final ISetController setController;

    public MatchController() {
        this.matchDAO = new MatchDAO();
        this.setController = new SetController();
    }

    @Override
    public void createMatch(int setsNumber, Player player1, Player player2) throws SQLException {
        initializeValues(setsNumber, player1, player2);
        simulateMatch();
    }

    private void initializeValues(int setsNumber, Player player1, Player player2) throws SQLException {

        match = new Match();
        match.setSetsNumber(setsNumber);
        match.setIdPlayer1(player1.getId());
        match.setIdPlayer2(player2.getId());
        matchDAO.addMatch(match);
    }

    private void simulateMatch() throws SQLException {
        int idSetWinner = setController.generateSetsForMatch(match);

        updateMatch(idSetWinner);
    }

    private void updateMatch(int idWinner) throws SQLException {
        match.setIdMatchWinner(idWinner);
        matchDAO.updateMatch(match);
    }
}
