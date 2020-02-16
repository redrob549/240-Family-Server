package services;

import models.User;

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
    public boolean Register () {
        return false;
    }

    /**
     * adds the new user data to the database.
     */
    private void CreateAccount() {

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
