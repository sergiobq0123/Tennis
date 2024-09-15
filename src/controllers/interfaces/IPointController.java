package controllers.interfaces;

import models.*;

import java.sql.SQLException;

public interface IPointController {
    Point createPoint(int game, Player player1, Player player2, int playerService, int idMatch) throws SQLException;
}
