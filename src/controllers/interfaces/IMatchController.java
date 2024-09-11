package controllers.interfaces;

import models.Match;
import models.Player;

public interface IMatchController {
    void createMatch(Match match, Player player1, Player player2);
}
