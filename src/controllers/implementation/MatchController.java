package controllers.implementation;

import controllers.interfaces.IMatchController;
import controllers.interfaces.ISetController;
import models.Match;
import models.Player;

public class MatchController implements IMatchController {

    @Override
    public void createMatch(Match match, Player player1, Player player2) {
        ISetController setController = new SetController();
        setController.createSet(match, player1, player2);
    }
}
