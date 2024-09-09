package controllers.interfaces;

import models.Match;
import models.Player;

public abstract class IMatchController {
    public abstract void createMatch(Match match, Player player1, Player player2);
}
