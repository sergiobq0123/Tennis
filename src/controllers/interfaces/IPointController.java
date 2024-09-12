package controllers.interfaces;

import models.*;

import java.sql.SQLException;

public interface IPointController {
    Point createPoint(int game, int player1, int player2, int playerService, String namePlayer1, String namePlayer2, int idMatch) throws SQLException;
}
