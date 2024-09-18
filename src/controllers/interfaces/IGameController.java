package controllers.interfaces;

import models.Game;
import models.Match;
import models.Player;
import models.Set;

import java.sql.SQLException;

public interface IGameController {
    int generateGamesForSet(Match match, Set set) throws SQLException;
}