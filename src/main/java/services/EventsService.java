package services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.EventDAO;
import FMSmodels.Event;

import java.sql.Connection;
import java.util.ArrayList;

public class EventsService {
    private String username;
    ArrayList<Event> eventList = new ArrayList<>();

    public EventsService(String name) {
        username = name;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void fillList() throws DataAccessException {
        Event[] events;
        Database db = new Database();
        Connection conn = db.openConnection();
        EventDAO eDao = new EventDAO(conn);
        events = eDao.findAll(username);
        db.closeConnection(true);
        for (int i = 0; i < events.length; i++) {
            eventList.add(events[i]);
        }
    }
}
