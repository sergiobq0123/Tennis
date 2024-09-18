package controllers.implementation;

import controllers.interfaces.IPointController;
import dao.MatchDAO;
import dao.PlayerDAO;
import dao.PointDAO;
import models.*;

import java.sql.SQLException;
import java.util.Random;

public class PointController implements IPointController {
    private static final String[] SCORE = {"0", "15", "30", "40", "AD"};
    private static final int FORTY_POINTS = 3;
    private static final int AD_POINTS = 4;
    private static final int MAX_CONSECUTIVE_FAULTS = 2;
    private static final int WINNING_POINTS = 7;
    private static final int MINIMUM_LEAD = 2;
    private static final String LACK_SERVICE = "Lack Service";
    private static final String SECOND_LACK_SERVICE = "Second Lack Service";
    private static final String POINT_SERVICE = "Point Service";
    private static final String POINT_REST = "Point Rest";

    private int idMatchShow;
    private int nameWidth;
    private String namePlayer1Show;
    private String namePlayer2Show;
    private int p1Points;
    private int p2Points;
    private int consecutiveFaults;
    private int lastPlayerWin;
    private boolean isDeuce;
    private boolean hasWinner;
    private int sumPointsPlayer1;
    private int sumPointsPlayer2;
    private int idWinner;

    private Point point;
    private Random random;
    private final PointDAO pointDAO;

    public PointController() {
        this.pointDAO = new PointDAO();
        this.random = new Random();
    }

    @Override
    public int generatePointsForGame(Match match, Set set, Game game, int currentService) throws SQLException {
        reset();
        initialNames(match);
        initializeValues(game, currentService);
        displayScore();
        generateRandomPoint();
        pointDAO.updatePoint(point);
        return point.getIdPointWinner();
    }

    @Override
    public int generatePointsForTieBreak(Match match, Set set, Game game, int currentService) throws SQLException {
        reset();
        initialNames(match);
        displayScore();
        generateRandomTieBreakPoint(match, game, currentService);
        return idWinner;
    }

    private void initialNames(Match match) throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO();
        Player player1 = playerDAO.getPlayer(match.getIdPlayer1());
        Player player2 = playerDAO.getPlayer(match.getIdPlayer2());
        setPlayersName(player1.getName(), player2.getName(), match.getId());
    }

    private void generateRandomTieBreakPoint(Match match, Game game, int currentService) throws SQLException {
        point = new Point(game.getId(), match.getIdPlayer1(), match.getIdPlayer2(), currentService);
        pointDAO.addPoint(point);
        int totalPoints = 0;

        while (!hasWinner) {
            generatePointTieBreak(random.nextInt(2));
            currentService = getNextServer(totalPoints, currentService, match);
            point.setIdPlayerService(currentService);

            totalPoints++;
        }
    }

    private void generatePointTieBreak(int action) throws SQLException {
        switch (action) {
            case 0 -> {
                sumPointsPlayer1++;
                idWinner = point.getIdPlayer1();
            }
            case 1 -> {
                sumPointsPlayer2++;
                idWinner = point.getIdPlayer2();
            }
        }
        checkWinnerForTieBreakPoint();

        updatePoint(point);

        displayScore();
    }

    private int getNextServer(int totalPoints, int currentService, Match match) {
        if (totalPoints == 0) {
            return currentService;
        }
        if (totalPoints % 2 == 1) {
            return currentService == match.getIdPlayer1() ? match.getIdPlayer2() : match.getIdPlayer1();
        }
        return currentService;
    }

    private void checkWinnerForTieBreakPoint() {
        boolean player1HasWon = sumPointsPlayer1 >= WINNING_POINTS && (sumPointsPlayer1 - sumPointsPlayer2) >= MINIMUM_LEAD;
        boolean player2HasWon = sumPointsPlayer2 >= WINNING_POINTS && (sumPointsPlayer2 - sumPointsPlayer1) >= MINIMUM_LEAD;

        if (player1HasWon || player2HasWon) {
            hasWinner = true;
        }
    }

    private void updatePoint(Point point) throws SQLException {

        point.setScorePlayer1(String.valueOf(sumPointsPlayer1));
        point.setScorePlayer2(String.valueOf(sumPointsPlayer2));
        point.setIdPointWinner(idWinner);

        pointDAO.updatePoint(point);
    }

    private void initializeValues(Game game, int idPlayerService) throws SQLException {
        Point point = new Point();
        this.point = point;
        point.setIdGame(game.getId());
        point.setIdPlayer1(game.getIdPlayer1());
        point.setIdPlayer2(game.getIdPlayer2());
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
                hasWinner = true;
            } else {
                p1Points++;
                point.setScorePlayer1(SCORE[p1Points]);
            }
        } else {
            if (p2Points == FORTY_POINTS) {
                point.setIdPointWinner(point.getIdPlayer2());
                hasWinner = true;
            } else {
                p2Points++;
                point.setScorePlayer2(SCORE[p2Points]);
            }
        }
    }

    private void updateScorePlayerDeuce(boolean isPlayer1) {
        if (isPlayer1) {
            p1Points++;
            if (p1Points == AD_POINTS + 1) {
                point.setIdPointWinner(point.getIdPlayer1());
                hasWinner = true;
            }
            else {
                if (p2Points == AD_POINTS) {
                    p2Points--;
                    point.setScorePlayer2(SCORE[p2Points]);
                    p1Points--;
                } else point.setScorePlayer1(SCORE[p1Points]);
            }
        } else {
            p2Points++;
            if (p2Points == AD_POINTS + 1) {
                point.setIdPointWinner(point.getIdPlayer2());
                hasWinner = true;
            } else {
                if (p1Points == AD_POINTS) {
                    p1Points--;
                    point.setScorePlayer1(SCORE[p1Points]);
                    p2Points--;
                } else point.setScorePlayer2(SCORE[p2Points]);
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
        isDeuce = p1Points == FORTY_POINTS && p2Points == FORTY_POINTS;
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
        System.out.println("\nMatch id : " + idMatchShow + " > " + pointType);
    }

    private void displayScore() {
        if (!hasWinner) {
            boolean isFault = consecutiveFaults == 1;

            String player1Prefix = point.getIdPlayer1() == point.getIdPlayerService() ? (isFault ? "+ " : "* ") : "  ";
            String player2Prefix = point.getIdPlayer2() == point.getIdPlayerService() ? (isFault ? "+ " : "* ") : "  ";

            String player1DisplayName = String.format("%-" + nameWidth + "s", namePlayer1Show);
            String player2DisplayName = String.format("%-" + nameWidth + "s", namePlayer2Show);

            System.out.println(player1Prefix + player1DisplayName + " : " + point.getScorePlayer1());
            System.out.println(player2Prefix + player2DisplayName + " : " + point.getScorePlayer2());

        } else {
            System.out.println("\nGame ball!!\n");
        }
    }

    private void reset() {
        idMatchShow = 0;
        nameWidth = 0;
        namePlayer1Show = "";
        namePlayer2Show = "";
        p1Points = 0;
        p2Points = 0;
        consecutiveFaults = 0;
        lastPlayerWin = 0;
        isDeuce = false;
        sumPointsPlayer1 = 0;
        sumPointsPlayer2 = 0;
        hasWinner = false;
        idWinner = 0;
    }
}
