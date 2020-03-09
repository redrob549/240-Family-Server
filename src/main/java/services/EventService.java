package services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.EventDAO;
import FMSmodels.Event;

import java.sql.Connection;

public class EventService {
    public EventService() {
    }

    /**
     * constructor to find a single event.
     * @param eventID the event ID to look up the event.
     */
    public  EventService(String eventID) {
        this.eventID = eventID;
    }

    public Event getEvent() throws DataAccessException {
        Event ret;
        Database db = new Database();
        Connection conn = db.openConnection();
        EventDAO eDao = new EventDAO(conn);
        ret = eDao.find(eventID);
        db.closeConnection(true);
        return ret;
    }

    private String eventID = "";

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
