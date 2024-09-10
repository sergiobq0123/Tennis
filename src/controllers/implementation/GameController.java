package controllers.implementation;

import controllers.interfaces.IGameController;
import models.*;

import java.util.List;

public class GameController implements IGameController {

    @Override
    public void createGame(Match match, Set set, Player player1, Player player2){
        PointController pointController = new PointController();
        Game game = new Game(1, player1, player2);
        List<Point> points = game.getPoints();

        points.add(pointController.createPoint(match, set, game, player1, player2, player1));
        // TODO añadir punto al ganador

        System.out.println(game);
    }

}
