package controllers.implementation;

import controllers.interfaces.IGameController;
import models.*;

import java.sql.SQLException;
import java.util.List;

public class GameController implements IGameController {

    @Override
    public void createGame(Match match, Set set, Player player1, Player player2) throws SQLException {
        PointController pointController = new PointController();
        Game game = new Game(1, player1, player2);
        List<Point> points = game.getPoints();

        points.add(pointController.createPoint(game.getId(), player1.getId(), player2.getId(), player1.getId(), player1.getName(), player2.getName(), match.getId()));
        // TODO añadir punto al ganador

        System.out.println(game);
    }

}
