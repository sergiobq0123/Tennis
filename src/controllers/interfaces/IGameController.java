package controllers.interfaces;

import models.Match;
import models.Player;
import models.Set;

public interface IGameController {
    void createGame(Match match, Set set, Player player1, Player player2);
}