package controllers.interfaces;

import models.*;

import java.sql.SQLException;

public interface IRegisterController {
    void addPlayer(Player player) throws SQLException;

    void getAllPlayers() throws SQLException;

    Player getPlayerById(int id) throws SQLException;

    void deletePlayer(Player player) throws SQLException;

    void updatePlayer(Player player) throws SQLException;
}