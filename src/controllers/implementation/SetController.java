package controllers.implementation;

import controllers.interfaces.IGameController;
import controllers.interfaces.ISetController;
import models.Match;
import models.Player;
import models.Set;

import java.sql.SQLException;

public class SetController implements ISetController {

    @Override
    public void createSet(Match match, Player player1, Player player2) throws SQLException {
        Set set = new Set(1, match.getId(), player1, player2);
        IGameController gameController = new GameController();
        gameController.createGame(match, set, player1, player2);
    }
}
