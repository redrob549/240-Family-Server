package responses;

import FMSmodels.Event;

import java.util.ArrayList;

public class EventsResponse extends Response {
    private ArrayList<Event> data;

    public EventsResponse(ArrayList<Event> eve) {
        data = eve;
        setSuccess(true);
    }

}
