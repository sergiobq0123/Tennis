package controllers.implementation;

import controllers.interfaces.IRegisterController;
import dao.connector.PlayerDAO;
import models.Player;

import java.sql.SQLException;

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
        System.out.println("no se ha podido añadir el usuario");
    }
}
