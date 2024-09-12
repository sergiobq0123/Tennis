package controllers.implementation;

import controllers.interfaces.IPointController;
import dao.PointDAO;
import models.*;

import java.sql.SQLException;
import java.util.Random;

public class PointController implements IPointController {
    private static final String[] SCORE = {"0", "15", "30", "40", "AD"};
    private static final int FORTY_POINTS = 3;
    private static final int AD_POINTS = 4;
    private static final int MAX_CONSECUTIVE_FAULTS = 2;

    private static final String LACK_SERVICE = "Lack Service";
    private static final String SECOND_LACK_SERVICE = "Second Lack Service";
    private static final String POINT_SERVICE = "Point Service";
    private static final String POINT_REST = "Point Rest";


    private static int idMatchShow;
    private int nameWidth;
    private String namePlayer1Show;
    private String namePlayer2Show;
    private int p1Points = 0;
    private int p2Points = 0;
    private int consecutiveFaults = 0;
    private int lastPlayerWin = 0;
    private boolean isDeuce = false;

    Point point;
    Random random;
    PointDAO pointDAO = new PointDAO();

    @Override
    public Point createPoint( int idGame, int idPlayer1, int idPlayer2, int idPlayerService, String namePlayer1, String namePlayer2, int idMatch) throws SQLException {
        setPlayersToPoint(idGame, idPlayer1, idPlayer2, idPlayerService);
        setPlayersName(namePlayer1, namePlayer2, idMatch);
        displayScore();
        generateRandomPoint();
        pointDAO.updatePoint(point);
        return point;
    }

    private void setPlayersToPoint(int idGame,int idPlayer1, int idPlayer2, int idPlayerService) throws SQLException {
        point = new Point();
        point.setIdGame(idGame);
        point.setIdPlayer1(idPlayer1);
        point.setIdPlayer2(idPlayer2);
        point.setIdPlayerService(idPlayerService);

        pointDAO.addPoint(point);
    }

    private void setPlayersName(String namePlayer1, String namePlayer2, int idMatch) {
        namePlayer1Show = namePlayer1;
        namePlayer2Show = namePlayer2;
        nameWidth = Math.max(namePlayer1Show.length(), namePlayer2Show.length());
        idMatchShow = idMatch;
    }

    private void generateRandomPoint() {
        random = new Random();
        while (point.getIdPointWinner() == null) {
            int action = random.nextInt(3);
            switch (action) {
                case 0 -> handleFault();
                case 1 -> updateScore(true);
                case 2 -> updateScore(false);
            }
        }
    }

    private void updateScore(boolean isPlayer1) {
        resetConsecutiveFaults();
        setLastPlayerWin(isPlayer1);

        if (!isDeuce) {
            updateScorePlayer(isPlayer1);
            verifyIsDeuce();
        } else {
            updateScorePlayerDeuce(isPlayer1);
        }
        displayHeader(lastPlayerWin == point.getIdPlayer1() ? POINT_SERVICE : POINT_REST);
        displayScore();
    }

    private void updateScorePlayer(boolean isPlayer1) {
        if (isPlayer1) {
            if (p1Points == FORTY_POINTS) {
                point.setIdPointWinner(point.getIdPlayer1());
            } else {
                p1Points++;
                point.setScorePlayer1(SCORE[p1Points]);
            }
        } else {
            if (p2Points == FORTY_POINTS) {
                point.setIdPointWinner(point.getIdPlayer2());
            } else {
                p2Points++;
                point.setScorePlayer2(SCORE[p2Points]);
            }
        }
    }

    private void updateScorePlayerDeuce(boolean isPlayer1) {
        if (isPlayer1) {
            p1Points++;
            if (p1Points == AD_POINTS + 1) point.setIdPointWinner(point.getIdPlayer1());
            else {
                if (p2Points == AD_POINTS) {
                    p2Points--;
                    point.setScorePlayer2(SCORE[p2Points]);
                    p1Points--;
                } else {
                    point.setScorePlayer1(SCORE[p1Points]);
                }
            }
        } else {
            p2Points++;
            if (p2Points == AD_POINTS + 1) point.setIdPointWinner(point.getIdPlayer2());
            else {
                if (p1Points == AD_POINTS) {
                    p1Points--;
                    point.setScorePlayer1(SCORE[p1Points]);
                    p2Points--;
                } else {
                    point.setScorePlayer2(SCORE[p2Points]);
                }
            }
        }
    }

    private void setLastPlayerWin(boolean isPlayer1) {
        if (isPlayer1) lastPlayerWin = point.getIdPlayer1();
        else lastPlayerWin = point.getIdPlayer2();
    }

    private void resetConsecutiveFaults() {
        consecutiveFaults = 0;
    }

    private void verifyIsDeuce() {
        if (p1Points == FORTY_POINTS && p2Points == FORTY_POINTS) {
            isDeuce = true;
        }
    }

    private void handleFault() {
        consecutiveFaults++;
        if (consecutiveFaults != MAX_CONSECUTIVE_FAULTS) {
            displayHeader(LACK_SERVICE);
            displayScore();
        } else {
            displayHeader(SECOND_LACK_SERVICE);
            updateScore(point.getIdPlayerService() == point.getIdPlayer1());
            resetConsecutiveFaults();
        }
    }

    private void displayHeader(String pointType) {
        System.out.println("\nMatch id : "+ idMatchShow +" > " + pointType);
    }

    private void displayScore() {
        if (point.getIdPointWinner() == null) {
            boolean isFault = consecutiveFaults == 1;

            String player1Prefix = point.getIdPlayer1() == point.getIdPlayerService() ? (isFault ? "+ " : "* ") : "  ";
            String player2Prefix = point.getIdPlayer2() == point.getIdPlayerService() ? (isFault ? "+ " : "* ") : "  ";

            String player1DisplayName = String.format("%-" + nameWidth + "s", namePlayer1Show);
            String player2DisplayName = String.format("%-" + nameWidth + "s", namePlayer2Show);

            System.out.println(player1Prefix + player1DisplayName + " : " + point.getScorePlayer1());
            System.out.println(player2Prefix + player2DisplayName + " : " + point.getScorePlayer2());

        } else {
            System.out.println("\nGame ball!!");
        }
    }

}
