package controllers.implementation;

import controllers.interfaces.ISetController;
import dao.SetDao;
import models.Game;
import models.Match;
import models.Player;
import models.Set;

import java.sql.SQLException;

public class SetController implements ISetController {
    private static final int WINNING_GAMES = 6;
    private static final int MINIMUM_LEAD = 2;

    private int sumGamesPlayer1;
    private int sumGamesPlayer2;

    private boolean isTieBreak = false;

    Set set;
    SetDao setDAO = new SetDao();
    GameController gameController;
    Player player1;
    Player player2;
    Match match;

    @Override
    public Set createSet(Match match, Player player1, Player player2) throws SQLException {
        initializeValues(match, player1, player2);
        simulateSet();
        return set;
    }

    private void initializeValues(Match match, Player player1, Player player2) throws SQLException {
        this.player1 = player1;
        this.player2 = player2;
        this.match = match;

        set = new Set();
        set.setIdPlayer1(player1.getId());
        set.setIdPlayer2(player2.getId());
        set.setIdMatch(match.getId());
        setDAO.addSet(set);
    }

    private void simulateSet() throws SQLException {
        while (set.getIdSetWinner() == null) {
            gameController = new GameController();
            Game game = gameController.createGame(match, set, player1, player2);
            if (game.getIdGameWinner() == game.getIdPlayer1()) {
                sumGamesPlayer1++;
            } else {
                sumGamesPlayer2++;
            }
            if (!this.isTieBreak) {
                verifyWinnerNormalSet();
            } else {
                updateSet(game.getIdGameWinner());
            }
        }
    }

    private void verifyWinnerNormalSet() throws SQLException {
        boolean player1HasWon = sumGamesPlayer1 >= WINNING_GAMES && (sumGamesPlayer1 - sumGamesPlayer2) >= MINIMUM_LEAD;
        boolean player2HasWon = sumGamesPlayer2 >= WINNING_GAMES && (sumGamesPlayer2 - sumGamesPlayer1) >= MINIMUM_LEAD;
        if (player1HasWon) {
            updateSet(player1.getId());
        } else if (player2HasWon) {
            updateSet(player2.getId());
        }
        if (sumGamesPlayer1 == WINNING_GAMES && sumGamesPlayer2 == WINNING_GAMES) {
            isTieBreak = true;
        }
    }
    private void updateSet(int idWinner) throws SQLException {
        set.setIdSetWinner(idWinner);
        set.setGamesPlayer1(sumGamesPlayer1);
        set.setGamesPlayer1(sumGamesPlayer2);

        setDAO.updateSet(set);
    }
}
