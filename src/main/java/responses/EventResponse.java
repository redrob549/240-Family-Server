package responses;

import FMSmodels.Event;
import com.google.gson.annotations.SerializedName;

public class EventResponse extends Response {
    private String eventID;
    @SerializedName("associatedUsername")
    private String associatedUser;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public EventResponse(Event eve) {
        eventID = eve.getEventID();
        associatedUser = eve.getAssociatedUser();
        personID = eve.getPersonID();
        latitude = eve.getLatitude();
        longitude = eve.getLongitude();
        country = eve.getCountry();
        city = eve.getCity();
        eventType = eve.getEventType();
        year = eve.getYear();
        setSuccess(true);
    }

}
