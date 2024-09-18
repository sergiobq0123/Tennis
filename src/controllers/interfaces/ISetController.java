package controllers.interfaces;

import models.Match;

import java.sql.SQLException;

public interface ISetController{
    int generateSetsForMatch(Match match) throws SQLException;
}
