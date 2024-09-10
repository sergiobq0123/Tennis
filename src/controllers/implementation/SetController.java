package controllers.implementation;

import controllers.interfaces.IGameController;
import controllers.interfaces.ISetController;
import models.Match;
import models.Player;

public class SetController implements ISetController {

    @Override
    public void createSet(Match match, Player player1, Player player2){
        IGameController gameController = new GameController();
        gameController.createGame(player1, player2);
    }
}
