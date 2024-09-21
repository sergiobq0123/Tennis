package controllers.implementation;

import controllers.interfaces.IGameController;
import controllers.interfaces.ISetController;
import dao.SetDao;
import models.Match;
import models.Set;

import java.sql.SQLException;

public class SetController implements ISetController {

    private static final int SETS_3 = 3;
    private static final int WINNING_SETS_3 = 2;

    private static final int SETS_5 = 5;
    private static final int WINNING_SETS_5 = 3;

    private int numberOfSets;
    private int setsWonPlayer1;
    private int setsWonPlayer2;
    private boolean hasWinner = false;
    private int setWinnerId;

    private final IGameController gameController;
    private final SetDao setDao;

    public SetController() {
        reset();
        gameController = new GameController();
        setDao = new SetDao();
    }

    @Override
    public int generateSetsForMatch(Match match) throws SQLException {
        numberOfSets = match.getSetsNumber();
        simulateSets(match);
        return setWinnerId;
    }

    private void simulateSets(Match match) throws SQLException {
        while (!hasWinner) {
            playSet(match);
        }
    }

    private void playSet(Match match) throws SQLException {
        Set currentSet = new Set(match.getId(), match.getIdPlayer1(), match.getIdPlayer2());
        setDao.addSet(currentSet);

        int gameWinnerId = gameController.generateGamesForSet(match, currentSet);

        if (gameWinnerId == currentSet.getIdPlayer1()) setsWonPlayer1++;
        else setsWonPlayer2++;
        setWinnerId = gameWinnerId;

        checkWinner();

        updateSet(currentSet);
    }

    private void checkWinner() {

        if (numberOfSets == SETS_3) {
            checkWinnerForSets(WINNING_SETS_3);
        } else if (numberOfSets == SETS_5) {
            checkWinnerForSets(WINNING_SETS_5);
        }
    }

    private void checkWinnerForSets(int setsToWin) {
        hasWinner = (setsWonPlayer1 == setsToWin || setsWonPlayer2 == setsToWin);
    }

    private void updateSet(Set set) throws SQLException {
        set.setIdSetWinner(setWinnerId);
        setDao.updateSet(set);
    }

    private void reset() {
        numberOfSets = 0;
        setsWonPlayer1 = 0;
        setsWonPlayer2 = 0;
        hasWinner = false;
        setWinnerId = 0;
    }
}
