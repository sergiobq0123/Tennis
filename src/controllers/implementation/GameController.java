package controllers.implementation;

import controllers.interfaces.IGameController;
import controllers.interfaces.IPointController;
import dao.GameDAO;
import models.Game;
import models.Match;
import models.Point;
import models.Set;

import java.sql.SQLException;

public class GameController implements IGameController {

    private static final int WINNING_POINTS = 6;
    private static final int MINIMUM_LEAD = 2;

    private int sumPointsPlayer1;
    private int sumPointsPlayer2;
    private boolean isTieBreak = false;
    private boolean isPlayer1Serving = true;
    private boolean hasWinner = false;
    private int idWinner;

    private final GameDAO gameDAO;
    private final IPointController pointController;

    public GameController() {
        gameDAO = new GameDAO();
        pointController = new PointController();
    }

    @Override
    public int generateGamesForSet(Match match, Set set) throws SQLException {
        reset();
        simulateGame(match, set);
        return idWinner;
    }

    private void simulateGame(Match match, Set set) throws SQLException {
        while (!hasWinner) {
            playGame(match, set);
        }
    }

    private void playGame(Match match, Set set) throws SQLException {
        Game currentGame = new Game(match.getIdPlayer1(), match.getIdPlayer2(), set.getId());
        gameDAO.addGame(currentGame);

        int currentServer = isPlayer1Serving ? set.getIdPlayer1() : set.getIdPlayer2();
        int idPointWinner;

        idPointWinner = !isTieBreak
                ? pointController.generatePointsForGame(match, set, currentGame, currentServer)
                : pointController.generatePointsForTieBreak(match, set, currentGame, currentServer);

        if (idPointWinner == currentGame.getIdPlayer1()) sumPointsPlayer1++;
        else sumPointsPlayer2++;
        idWinner = idPointWinner;

        checkWinner();

        updateGame(currentGame);

        isPlayer1Serving = !isPlayer1Serving;
    }

    private void checkWinner() {
        if (isTieBreak) hasWinner = true;
        else checkWinnerForNormalGame();
    }

    private void checkWinnerForNormalGame() {
        boolean player1HasWon = sumPointsPlayer1 >= WINNING_POINTS && (sumPointsPlayer1 - sumPointsPlayer2) >= MINIMUM_LEAD;
        boolean player2HasWon = sumPointsPlayer2 >= WINNING_POINTS && (sumPointsPlayer2 - sumPointsPlayer1) >= MINIMUM_LEAD;

        if (player1HasWon || player2HasWon) {
            hasWinner = true;
        }

        if (sumPointsPlayer1 == WINNING_POINTS && sumPointsPlayer2 == WINNING_POINTS) {
            isTieBreak = true;
        }
    }

    private void updateGame(Game game) throws SQLException {
        game.setPointsPlayer1(sumPointsPlayer1);
        game.setPointsPlayer2(sumPointsPlayer2);
        game.setIdGameWinner(idWinner);

        if(hasWinner) {
            game.setGameOver(true);
        }

        gameDAO.updateGame(game);
    }

    private void reset() {
        sumPointsPlayer1 = 0;
        sumPointsPlayer2 = 0;
        isTieBreak = false;
        hasWinner = false;
        idWinner = 0;
    }
}
