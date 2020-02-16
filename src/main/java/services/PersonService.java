package services;

import models.AuthToken;

public class PersonService {
    public PersonService() {
    }

    /**
     * constructor for grabbing the users ancestor list.
     * @param token AuthToken representing the current user.
     */
    public PersonService(AuthToken token) {
        myAuthToken = token;
    }

    /**
     * constructor for grabbing a single person's data
     * @param token AuthToken to authorize the access.
     * @param person the ID of the person to find.
     */
    public PersonService(AuthToken token, String person) {
        myAuthToken = token;
        personID = person;
    }

    public AuthToken getMyAuthToken() {
        return myAuthToken;
    }

    public void setMyAuthToken(AuthToken myAuthToken) {
        this.myAuthToken = myAuthToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    private AuthToken myAuthToken;
    private String personID = "";
}
