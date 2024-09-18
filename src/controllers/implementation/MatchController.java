package controllers.implementation;

import controllers.interfaces.IMatchController;
import controllers.interfaces.ISetController;
import dao.MatchDAO;
import models.Match;
import models.Player;

import java.sql.SQLException;

public class MatchController implements IMatchController {

    private final MatchDAO matchDAO;
    private final ISetController setController;

    public MatchController() {
        this.matchDAO = new MatchDAO();
        this.setController = new SetController();
    }

    @Override
    public void createMatch(int setsNumber, Player player1, Player player2) throws SQLException {
        Match match = new Match(setsNumber, player1.getId(), player2.getId());
        matchDAO.addMatch(match);
        simulateMatch(match);
    }

    private void simulateMatch(Match match) throws SQLException {
        int idSetWinner = setController.generateSetsForMatch(match);
        updateMatch(match, idSetWinner);
    }

    private void updateMatch(Match match, int idWinner) throws SQLException {
        match.setIdMatchWinner(idWinner);
        matchDAO.updateMatch(match);
    }
}
