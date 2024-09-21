package controllers.interfaces;

import java.sql.SQLException;

public interface IHistoryController {
    void readPlayer(int id) throws SQLException;
    void readMatch(int id) throws SQLException;
}
