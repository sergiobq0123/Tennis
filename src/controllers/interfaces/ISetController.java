package controllers.interfaces;

import models.Match;
import models.Player;

public interface ISetController{
    void createSet(Match match, Player player1, Player player2);
}
