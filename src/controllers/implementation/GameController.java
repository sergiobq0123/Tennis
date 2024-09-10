package controllers.implementation;

import controllers.interfaces.IGameController;
import models.Player;

public class GameController implements IGameController {

    @Override
    public void createGame(Player player1, Player player2){
        PointController pointController = new PointController();
        Player player = pointController.createPoint(player1, player2, player1);
        System.out.println(player);
    }
}
