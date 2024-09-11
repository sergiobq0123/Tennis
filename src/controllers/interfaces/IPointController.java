package controllers.interfaces;

import models.*;

public interface IPointController {
    Point createPoint(Match match, Set set, Game game, Player player1, Player player2, Player playerService);
}
