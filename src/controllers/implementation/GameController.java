package controllers.implementation;

import models.Match;
import models.Player;
import models.Set;

public class GameController {
    public void createGame(Player player1, Player player2){
        PointController pointController = new PointController();
        Player player = pointController.createPoint(player1, player2, player1);
        System.out.println(player);
    }
}
