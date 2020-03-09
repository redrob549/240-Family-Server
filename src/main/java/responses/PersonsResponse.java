package responses;

import FMSmodels.Person;

import java.util.ArrayList;

public class PersonsResponse extends Response {
    private ArrayList<Person> data;

    public PersonsResponse(ArrayList<Person> per) {
        data = per;
        setSuccess(true);
    }

}
