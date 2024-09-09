package controllers.implementation;

import controllers.interfaces.IMatchController;
import models.Match;
import models.Player;

public class MatchController extends IMatchController {

    public void createMatch(Match match, Player player1, Player player2) {
        SetController setController = new SetController();
        setController.createSet(match, player1, player2);
    }
}
