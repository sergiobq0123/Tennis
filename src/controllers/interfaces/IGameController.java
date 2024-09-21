package controllers.interfaces;

import models.Match;
import models.Set;

import java.sql.SQLException;

public interface IGameController {
    int generateGamesForSet(Match match, Set set) throws SQLException;
}