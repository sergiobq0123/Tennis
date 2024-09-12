package controllers.implementation;

import controllers.interfaces.IRegisterController;
import dao.PlayerDAO;
import models.Player;

import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterController implements IRegisterController {

    private PlayerDAO playerDAO;

    public RegisterController() {
        this.playerDAO = new PlayerDAO();
    }

    @Override
    public void addPlayer(Player player) throws SQLException {
        if(!playerDAO.playerExists(player)){
            playerDAO.addPlayer(player);
            System.out.println(player.toString());
        }
    }

    @Override
    public void getAllPlayers() throws SQLException {
        ArrayList<Player> players = playerDAO.getAllPlayers();
        System.out.println("'''PRINTING LIST OF PLAYERS '''");
        for (Player player : players) {
            System.out.println(player.toString());
        }
    }

    @Override
    public Player getPlayerById(int id) throws SQLException {
        return playerDAO.getPlayer(id);
    }

    @Override
    public void deletePlayer(Player player) throws SQLException {
        if(playerDAO.playerExists(player)){
            playerDAO.deletePlayer(player);
        }
    }

    @Override
    public void updatePlayer(Player player) throws SQLException {
            playerDAO.updateUser(player);
    }
}
