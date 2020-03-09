package services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.UserDAO;
import FMSmodels.User;

import java.sql.Connection;

public class RegisterService {
    public RegisterService() {
    }
    public RegisterService(User user) {
        myUser = user;
    }

    /**
     * performs the registration functions when making a new user.
     * @return true if user is successfully registered.
     */
    public boolean Register () throws DataAccessException {
        try {
            CreateAccount();

            return true;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception f) {
            return false;
        }
    }

    /**
     * adds the new user data to the database.
     */
    private void CreateAccount() throws DataAccessException {
        Database db = new Database();
        try {

            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.insert(myUser);
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            db.closeConnection(false);
            throw e;
        }
    }

    /**
     * generates 4 generations of people for the user.
     */
    private void CreateAncestorData() {

    }
    private User myUser;

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }
}
