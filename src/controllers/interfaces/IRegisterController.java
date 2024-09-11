package controllers.interfaces;

import models.*;

import java.sql.SQLException;

public interface IRegisterController{
    void addPlayer(Player player) throws SQLException;
}
