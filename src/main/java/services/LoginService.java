package services;

import DAOs.AuthTokenDAO;
import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.UserDAO;
import FMSmodels.AuthToken;
import FMSmodels.User;

import java.sql.Connection;
import java.util.UUID;

public class LoginService {
    public LoginService() {
    }

    /**
     *
     * @param user a user object representing the person signing in.
     */
    public LoginService(User user) {
        myUser = user;
    }

    /**
     *
     * @return return true if username and password match a user in the database.
     */
    public boolean login() throws DataAccessException {
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            User tempUser = uDao.findUser(myUser.getUserName());
            db.closeConnection(true);
            String actual = tempUser.getPassword();
            String attempt = myUser.getPassword();
            if (attempt.equals(actual)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (DataAccessException e){
            db.closeConnection(false);
            return false;
        }
    }

    public AuthToken createAuthToken() throws DataAccessException {
        Database db = new Database();
        try {
            AuthToken ret = new AuthToken(UUID.randomUUID().toString() ,myUser.getUserName());
            Connection conn = db.openConnection();
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            aDao.insert(ret);
            db.closeConnection(true);
            return ret;
        }
        catch (DataAccessException e){
            db.closeConnection(false);
            throw e;
        }
    }

    public String getUserID() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();
        UserDAO uDao = new UserDAO(conn);
        String ID = uDao.findUser(myUser.getUserName()).getPersonID();
        db.closeConnection(true);
        return ID;
    }

    private User myUser;

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }
}
