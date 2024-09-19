import controllers.implementation.*;
import controllers.interfaces.*;
import dao.PlayerDAO;
import models.*;


import java.sql.Date;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws SQLException {

        //PLAYERS BBDD-
//
//        Player player1 = new Player(1, "Rafael Nadal");
//        Player player2 = new Player(2, "Djokovic");
//
//        PlayerDAO playerDAO = new PlayerDAO();
//        playerDAO.addPlayer(player1);
//        playerDAO.addPlayer(player2);

        /**
        IPlayerController registerController = new PlayerController();
        registerController.addPlayer(player1);
        registerController.addPlayer(player2);
        player2.setName("Roger Federer");
        registerController.updatePlayer(player2);
        registerController.deletePlayer(player1);
        registerController.getAllPlayers();

        System.out.println("Obtencion de usuario mediante BBDD");
        Player player3 = registerController.getPlayerById(2);
        */

//        IMatchController matchController = new MatchController();
//        matchController.createMatch(5,player1, player2);

        HistoryController historyController = new HistoryController();
        System.out.println("READ PLAYER");
        historyController.readPlayer(1);

        System.out.println("READ MATCH");
        historyController.readMatch(12);

    }
}