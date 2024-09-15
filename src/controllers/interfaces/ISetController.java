package controllers.interfaces;

import models.Match;
import models.Player;
import models.Set;

import java.sql.SQLException;

public interface ISetController{
    Set createSet(Match match, Player player1, Player player2) throws SQLException;
}
