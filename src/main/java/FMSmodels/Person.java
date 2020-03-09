package FMSmodels;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Person {
    private String personID;
    @SerializedName("associatedUsername")
    private String associatedUser;
    private String firstName;
    private String lastName;
    private String gender;
    private String motherID;
    private String fatherID;
    private String spouseID;

    public Person(String personID, String associatedUser, String firstName, String lastName, String gender, String motherID, String fatherID, String spouseID) {
        this.personID = personID;
        this.associatedUser = associatedUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.motherID = motherID;
        this.fatherID = fatherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUser() {
        return associatedUser;
    }

    public void setAssociatedUser(String associatedUser) {
        this.associatedUser = associatedUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) &&
                Objects.equals(associatedUser, person.associatedUser) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(spouseID, person.spouseID);
    }
}
