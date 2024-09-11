import controllers.implementation.MatchController;
import controllers.interfaces.IMatchController;
import models.Match;
import models.Player;

import java.time.Clock;
import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Player player1 = new Player(1, "Rafael Nadal");
        Player player2 = new Player(2, "Roger");

        Match match = new Match(1, Instant.now(Clock.systemUTC()), 3, player1, player2);

        IMatchController matchController = new MatchController();
        matchController.createMatch(match, player1, player2);
    }
}