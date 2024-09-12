package controllers.interfaces;

import models.Match;
import models.Player;

import java.sql.SQLException;

public interface ISetController{
    void createSet(Match match, Player player1, Player player2) throws SQLException;
}
