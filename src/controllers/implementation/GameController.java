package controllers.implementation;

import controllers.interfaces.IGameController;
import dao.GameDAO;
import models.*;

import java.sql.SQLException;

public class GameController implements IGameController {
    private static final int WINNING_POINTS = 6;
    private static final int MINIMUM_LEAD = 2;

    private int sumPointsPlayer1;
    private int sumPointsPlayer2;
    private boolean isTieBreak = false;
    private boolean isPlayer1Serving = true;

    Game game;
    GameDAO gameDAO = new GameDAO();
    PointController pointController;
    Player player1;
    Player player2;
    Set set;
    Match match;


    @Override
    public Game createGame(Match match, Set set, Player player1, Player player2) throws SQLException {
        initializeValues(match, player1, player2, set);
        simulateGame();
        return game;
    }

    private void initializeValues(Match match, Player player1, Player player2, Set set) throws SQLException {
        this.player1 = player1;
        this.player2 = player2;
        this.set = set;
        this.match = match;

        game = new Game();
        game.setIdPlayer1(player1.getId());
        game.setIdPlayer2(player2.getId());
        game.setIdSet(set.getId());

        gameDAO.addGame(game);
    }

    private void simulateGame() throws SQLException {

        while (game.getIdGameWinner() == null) {
            pointController = new PointController();
            Player currentServer = isPlayer1Serving ? player1 : player2;
            Point point = pointController.createPoint(game.getId(), player1, player2, currentServer.getId(), match.getId());

            if (point.getIdPointWinner() == game.getIdPlayer1()) {
                sumPointsPlayer1++;
            } else {
                sumPointsPlayer2++;
            }

            if (!this.isTieBreak) {
                verifyWinnerNormalGame();
            }else{
                updateGame(point.getIdPointWinner());
            }
            isPlayer1Serving = !isPlayer1Serving;
        }
    }

    private void verifyWinnerNormalGame() throws SQLException {
        boolean player1HasWon = sumPointsPlayer1 >= WINNING_POINTS && (sumPointsPlayer1 - sumPointsPlayer2) >= MINIMUM_LEAD;
        boolean player2HasWon = sumPointsPlayer2 >= WINNING_POINTS && (sumPointsPlayer2 - sumPointsPlayer1) >= MINIMUM_LEAD;

        if (player1HasWon) {
            updateGame(player1.getId());
        } else if (player2HasWon) {
            updateGame(player2.getId());
        }

        if (sumPointsPlayer1 == WINNING_POINTS && sumPointsPlayer2 == WINNING_POINTS) {
            isTieBreak = true;
        }
    }

    private void updateGame(int idWinner) throws SQLException {
        game.setIdGameWinner(idWinner);
        game.setPointsPlayer1(sumPointsPlayer1);
        game.setPointsPlayer2(sumPointsPlayer2);

        gameDAO.updateGame(game);
    }

}
