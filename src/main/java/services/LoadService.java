package services;

import models.Event;
import models.Person;
import models.User;

public class LoadService {
    public LoadService() {
    }
    public LoadService(User[] userList, Person[] personList, Event[] eventList) {
        myUserList = userList;
        myPersonList = personList;
        myEventList = eventList;
    }

    /**
     * clears the database, then fills it with the passed in data.
     * @return true if the operation was successful.
     */
    public boolean Load() {
        return false;
    }

    public User[] getMyUserList() {
        return myUserList;
    }

    public void setMyUserList(User[] myUserList) {
        this.myUserList = myUserList;
    }

    public Person[] getMyPersonList() {
        return myPersonList;
    }

    public void setMyPersonList(Person[] myPersonList) {
        this.myPersonList = myPersonList;
    }

    public Event[] getMyEventList() {
        return myEventList;
    }

    public void setMyEventList(Event[] myEventList) {
        this.myEventList = myEventList;
    }

    private User[] myUserList;
    private Person[] myPersonList;
    private Event[] myEventList;
}
