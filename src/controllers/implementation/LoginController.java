package controllers.implementation;

import controllers.interfaces.ILoginController;
import dao.RefereeDAO;
import models.Referee;

import java.sql.SQLException;

public class LoginController implements ILoginController {

    private final RefereeDAO refereeDAO;

    public LoginController() {
        refereeDAO = new RefereeDAO();
    }

    @Override
    public void registerReferee(Referee ref) throws SQLException {
        if(!refereeDAO.refereeExists(ref)){
            refereeDAO.addReferee(ref);
            ref.setId(refereeDAO.getReferee(ref.getId()).getId());
        }
        else {
            System.out.println("El arbitro ya existe");
        }

    }
    @Override
    public void loginReferee(Referee refLogin) throws SQLException {
        String refereeName = refLogin.getName();
        String refereePass = refLogin.getPassword();
        Referee referee = refereeDAO.getRefereeByName(refereeName);
        if (refereeDAO.refereeExists(refLogin) && refereePass.equals(referee.getPassword())) {
            System.out.println("El arbitro se ha logueado");
        }
        else System.out.println("No se ha podido loguear");
    }
}
