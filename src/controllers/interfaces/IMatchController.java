package controllers.interfaces;

import models.Match;
import models.Player;

import java.sql.SQLException;

public interface IMatchController {
    void createMatch(int setsNumber,Player player1, Player player2) throws SQLException;
}
