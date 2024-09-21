package controllers.implementation;

import controllers.interfaces.IHistoryController;
import dao.*;
import models.Match;
import models.Player;

import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryController implements IHistoryController {

    private final PlayerDAO playerDAO;
    private final PointDAO pointDAO;
    private final GameDAO gameDAO;
    private final SetDao setDao;
    private final MatchDAO matchDAO;

    public HistoryController(){
        playerDAO = new PlayerDAO();
        pointDAO = new PointDAO();
        gameDAO = new GameDAO();
        setDao = new SetDao();
        matchDAO = new MatchDAO();
    }

    @Override
    public void readPlayer(int id) throws SQLException {
        Player player = playerDAO.getPlayer(id);
        System.out.println(player.toString());
        ArrayList<Match> matches = matchDAO.getMatchesByPlayerId(id);
        for (Match match : matches){
            int opponentId = match.foeId(id);
            Player opponent = playerDAO.getPlayer(opponentId);
            System.out.printf("%s; name: %s; sets: %s; %s%n",
                    match,
                    opponent.getName(),
                    setDao.getSetsByMatch(match),
                    (id == match.getIdMatchWinner() ? " Winner " : " Looser"));
        }
        System.out.println("Winner:" +matchDAO.getUserWinPercentage(id) + "%");
    }
    @Override
    public void readMatch(int matchID) throws SQLException {
        Match match = matchDAO.getMatch(matchID);
        Player player1 = playerDAO.getPlayer(match.getIdPlayer1());
        Player player2 = playerDAO.getPlayer(match.getIdPlayer2());
        ArrayList<Integer> setsId = setDao.getSetsIdByMatch(matchID);
        ArrayList<Integer> player1Res = new ArrayList<>();
        ArrayList<Integer> player2Res = new ArrayList<>();

        for (Integer setId : setsId){
            player1Res.add(gameDAO.getUserSets(matchID,setId,player1.getId()));
            player2Res.add(gameDAO.getUserSets(matchID,setId,player2.getId()));
        }
        System.out.println(match);
        printUserSets(player1, player1Res);
        printUserSets(player2, player2Res);
    }

    private void printUserSets(Player player, ArrayList<Integer> list){
        System.out.printf("%s; sets: %s%n", player, list);
    }

}

