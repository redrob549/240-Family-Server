package responses;

import FMSmodels.Person;
import com.google.gson.annotations.SerializedName;

public class PersonResponse extends Response {
    private String personID;
    @SerializedName("associatedUsername")
    private String associatedUser;
    private String firstName;
    private String lastName;
    private String gender;
    private String motherID;
    private String fatherID;
    private String spouseID;

    public PersonResponse(Person per) {
        personID = per.getPersonID();
        associatedUser = per.getAssociatedUser();
        firstName = per.getFirstName();
        lastName = per.getLastName();
        gender = per.getGender();
        motherID = per.getMotherID();
        fatherID = per.getFatherID();
        spouseID = per.getSpouseID();
        setSuccess(true);
    }

}
