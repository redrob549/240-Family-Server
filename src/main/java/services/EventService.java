package services;

import models.AuthToken;

public class EventService {
    public EventService() {
    }

    /**
     * constructor for finding all events of the user's family
     * @param token provides authorization and the user data
     */
    public EventService(AuthToken token) {
        myToken = token;
    }

    /**
     * constructor to find a single event.
     * @param token provides authorization.
     * @param event the event ID to look up the event.
     */
    public  EventService(AuthToken token, String event) {
        myToken = token;
        eventID = event;
    }
    private AuthToken myToken;
    private String eventID = "";

    public AuthToken getMyToken() {
        return myToken;
    }

    public void setMyToken(AuthToken myToken) {
        this.myToken = myToken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
