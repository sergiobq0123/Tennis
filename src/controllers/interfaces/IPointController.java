package controllers.interfaces;

import models.*;

import java.sql.SQLException;

public interface IPointController {
    int generatePointsForGame(Match match, Set set, Game game, int currentService) throws SQLException;
    int generatePointsForTieBreak(Match match, Set set, Game game, int currentService) throws SQLException;
}
