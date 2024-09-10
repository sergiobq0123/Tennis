package controllers.interfaces;

import models.Player;

public interface IPointController {
    Player createPoint(Player player1, Player player2, Player playerService);
}
