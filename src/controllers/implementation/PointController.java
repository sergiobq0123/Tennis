package controllers.implementation;

import controllers.interfaces.IPointController;
import models.Player;
import models.Point;

import java.util.Random;

public class PointController implements IPointController {
    private static final String[] SCORE = {"0", "15", "30", "40", "AD"};
    private static final int MAX_POINTS = 4;
    private static final int MAX_POINTS_WITH_DEUCE = 5;
    private static final int MAX_CONSECUTIVE_FAULTS = 2;
    private static final String LACK_SERVICE = "Lack Service";
    private static final String SECOND_LACK_SERVICE = "Second Lack Service";
    private static final String POINT_SERVICE = "Point Service";
    private static final String POINT_REST = "Point Rest";
    private static int NAME_WIDTH = 0;

    private int p1Points = 0;
    private int p2Points = 0;
    private boolean isGameOver = false;
    private int consecutiveFaults = 0;
    private int lastPlayerWin = 0;
    Point point = new Point();

    @Override
    public Player createPoint(Player player1, Player player2, Player playerService) {
        setPlayersToPoint(player1, player2, playerService);
        Random random = new Random();

        while (!isGameOver) {
            int action = random.nextInt(3);
            switch (action) {
                case 0 -> handleFault();
                case 1 -> updateScore(true, true);
                case 2 -> updateScore(false, true);
            }

            displayScore();

            if (!isGameOver) {
                isGameOver = isWinner();
            }

        }
        return determineWinner(player1, player2);
    }

    private void setPlayersToPoint(Player player1, Player player2, Player playerService) {
        point.setPlayer1(player1);
        point.setPlayer2(player2);
        point.setPlayerService(playerService);
        NAME_WIDTH = Math.max(player1.getName().length(), player2.getName().length());
    }

    private void updateScore(boolean isPlayer1, boolean showScore) {
        consecutiveFaults = 0;
        updatePlayerScore(isPlayer1);

        if (showScore) {
            displayHeader(lastPlayerWin == point.getPlayer1().getId() ? POINT_SERVICE : POINT_REST);
        }
    }

    private void updatePlayerScore(boolean isPlayer1) {

        if (isPlayer1) {
            lastPlayerWin = point.getPlayer1().getId();
            if (p2Points == MAX_POINTS) {
                p2Points--;
                point.setScorePlayer2(SCORE[p2Points]);
            } else {
                if (p1Points + 1 == MAX_POINTS_WITH_DEUCE) {
                    isGameOver = true;
                } else {
                    p1Points++;
                    point.setScorePlayer1(SCORE[p1Points]);
                }
            }
        } else {
            lastPlayerWin = point.getPlayer2().getId();
            if (p1Points == MAX_POINTS) {
                p1Points--;
                point.setScorePlayer1(SCORE[p1Points]);
            } else {
                if (p2Points + 1 == MAX_POINTS_WITH_DEUCE) {
                    isGameOver = true;
                } else {
                    p2Points++;
                    point.setScorePlayer2(SCORE[p2Points]);
                }
            }
        }
    }

    private boolean isWinner() {
        return (p1Points >= MAX_POINTS - 1 || p2Points >= MAX_POINTS - 1) && Math.abs(p1Points - p2Points) >= 1;
    }

    private void handleFault() {
        consecutiveFaults++;
        if (consecutiveFaults != MAX_CONSECUTIVE_FAULTS) {
            displayHeader(LACK_SERVICE);
        } else {
            displayHeader(SECOND_LACK_SERVICE);
            updateScore(!point.getPlayerService().equals(point.getPlayer1()), false);
            consecutiveFaults = 0;
        }
    }

    private Player determineWinner(Player player1, Player player2) {
        return p1Points > p2Points ? player1 : player2;
    }

    private void displayScore() {
        boolean isFault = consecutiveFaults == 1;

        String player1Prefix = point.getPlayer1().equals(point.getPlayerService())
                ? (isFault ? "+ " : "* ")
                : "  ";
        String player2Prefix = point.getPlayer2().equals(point.getPlayerService())
                ? (isFault ? "+ " : "* ")
                : "  ";

        String player1DisplayName = String.format("%-" + NAME_WIDTH + "s", point.getPlayer1().getName());
        String player2DisplayName = String.format("%-" + NAME_WIDTH + "s", point.getPlayer2().getName());

        System.out.println(player1Prefix + player1DisplayName + " : " + point.getScorePlayer1());
        System.out.println(player2Prefix + player2DisplayName + " : " + point.getScorePlayer2());
    }

    private void displayHeader(String pointType) {
        System.out.println("\nMatch id : 1 > " + pointType);
    }
}
