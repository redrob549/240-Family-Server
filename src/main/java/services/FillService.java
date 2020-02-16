package services;

import models.User;

public class FillService {

    public FillService() {
    }
    public FillService(User user) {
        myUser = user;
    }
    public FillService(User user, int gens) {
        myUser = user;
        generations = gens;
    }

    /**
     * fills out the ancestors of the user up to the specified number of generations.
     * @return true if the the operation was successful.
     */
    public boolean Fill() {
        return false;
    }
    private User myUser;
    private int generations = 4; //defaults to 4 in no number is passed in.

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
