package controllers.interfaces;

import models.Match;
import models.Player;
import models.Set;

import java.sql.SQLException;

public interface IGameController {
    void createGame(Match match, Set set, Player player1, Player player2) throws SQLException;
}