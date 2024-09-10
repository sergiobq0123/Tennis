package controllers.implementation;

import controllers.interfaces.IPointController;
import models.*;

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

    private static int NAME_WIDTH = 0;
    private static int idMatch;

    private int p1Points = 0;
    private int p2Points = 0;
    private int consecutiveFaults = 0;
    private int lastPlayerWin = 0;
    private boolean isDeuce = false;

    Point point;
    Random random;

    @Override
    public Point createPoint(Match match, Set set, Game game, Player player1, Player player2, Player playerService) {
        setPlayersToPoint(match, player1, player2, playerService);
        displayScore();
        generateRandomPoint();
        return point;
    }

    private void setPlayersToPoint(Match match, Player player1, Player player2, Player playerService) {
        point = new Point();
        point.setPlayer1(player1);
        point.setPlayer2(player2);
        point.setPlayerService(playerService);
        idMatch = match.getId();
        NAME_WIDTH = Math.max(player1.getName().length(), player2.getName().length());
    }

    private void generateRandomPoint() {
        random = new Random();
        while (point.getPointWinner() == null) {
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
        displayHeader(lastPlayerWin == point.getPlayer1().getId() ? POINT_SERVICE : POINT_REST);
        displayScore();
    }

    private void updateScorePlayer(boolean isPlayer1) {
        if (isPlayer1) {
            if (p1Points == FORTY_POINTS) {
                point.setPointWinner(point.getPlayer1());
            } else {
                p1Points++;
                point.setScorePlayer1(SCORE[p1Points]);
            }
        } else {
            if (p2Points == FORTY_POINTS) {
                point.setPointWinner(point.getPlayer2());
            } else {
                p2Points++;
                point.setScorePlayer2(SCORE[p2Points]);
            }
        }
    }

    private void updateScorePlayerDeuce(boolean isPlayer1) {
        if (isPlayer1) {
            p1Points++;
            if (p1Points == AD_POINTS + 1) point.setPointWinner(point.getPlayer1());
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
            if (p2Points == AD_POINTS + 1) point.setPointWinner(point.getPlayer2());
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
        if (isPlayer1) lastPlayerWin = point.getPlayer1().getId();
        else lastPlayerWin = point.getPlayer2().getId();
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
            updateScore(!point.getPlayerService().equals(point.getPlayer1()));
            resetConsecutiveFaults();
        }
    }

    private void displayHeader(String pointType) {
        System.out.println("\nMatch id : "+ idMatch +" > " + pointType);
    }

    private void displayScore() {
        if (point.getPointWinner() == null) {
            boolean isFault = consecutiveFaults == 1;

            String player1Prefix = point.getPlayer1().equals(point.getPlayerService()) ? (isFault ? "+ " : "* ") : "  ";
            String player2Prefix = point.getPlayer2().equals(point.getPlayerService()) ? (isFault ? "+ " : "* ") : "  ";

            String player1DisplayName = String.format("%-" + NAME_WIDTH + "s", point.getPlayer1().getName());
            String player2DisplayName = String.format("%-" + NAME_WIDTH + "s", point.getPlayer2().getName());

            System.out.println(player1Prefix + player1DisplayName + " : " + point.getScorePlayer1());
            System.out.println(player2Prefix + player2DisplayName + " : " + point.getScorePlayer2());

        } else {
            System.out.println("\nGame ball!!");
        }
    }

}
