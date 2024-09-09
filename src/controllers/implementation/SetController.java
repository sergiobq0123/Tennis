package controllers.implementation;

import models.Match;
import models.Player;

public class SetController {

    public void createSet(Match match, Player player1, Player player2){
        GameController gameController = new GameController();
        gameController.createGame(player1, player2);
    }
}
