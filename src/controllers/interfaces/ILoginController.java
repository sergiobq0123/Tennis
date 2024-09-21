package controllers.interfaces;

import models.Referee;

import java.sql.SQLException;

public interface ILoginController {
    void registerReferee(Referee ref) throws SQLException;
    void loginReferee(Referee refLogin) throws SQLException;
}