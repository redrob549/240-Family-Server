package services;

import models.User;

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
    public boolean login()
    {
        return false;
    }
    private User myUser;

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }
}
