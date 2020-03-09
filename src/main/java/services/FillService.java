package services;

import DAOs.*;
import FMSmodels.Event;
import FMSmodels.Person;
import FMSmodels.User;
import server.Deserializer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class FillService {

    public FillService() {
    }
    public FillService(String user) {
        userName = user;
    }
    public FillService(String user, int gens) {
        userName = user;
        generations = gens;
    }

    /**
     * fills out the ancestors of the user up to the specified number of generations.
     * @return true if the the operation was successful.
     */
    public boolean Fill() throws IOException {
        Deserializer ds = new Deserializer();
        File fNameList = new File("json/fnames.json");
        NamePOJO fNames = ds.parseNameList(fNameList);
        File mNameList = new File("json/mnames.json");
        NamePOJO mNames = ds.parseNameList(mNameList);
        File sNameList = new File("json/snames.json");
        NamePOJO sNames = ds.parseNameList(sNameList);
        File locationList = new File("json/locations.json");
        LocationPOJO locations = ds.parseLocationList(locationList);
        try {
            deleteAllAssociated();
            Database db = new Database();
            UserDAO uDao = new UserDAO(db.openConnection());
            User rootUser = uDao.findUser(userName);
            db.closeConnection(true);
            Person rootPerson = new Person(rootUser.getPersonID(),
                                            rootUser.getUserName(),
                                            rootUser.getFirstName(),
                                            rootUser.getLastName(),
                                            rootUser.getGender(),
                                            UUID.randomUUID().toString(),
                                            UUID.randomUUID().toString(),
                                            null);
            int rootYear = 2000;
            int nextGenYear = 1975;
            LocationPOJO.Locale tempLocale = locations.getRandomLocale();
            Event rootBirth = new Event(UUID.randomUUID().toString(),
                                        userName,
                                        rootPerson.getPersonID(),
                                        Double.parseDouble(tempLocale.getLatitude()),
                                        Double.parseDouble(tempLocale.getLongitude()),
                                        tempLocale.getCountry(),
                                        tempLocale.getCity(),
                                        "Birth",
                                        rootYear
                                        );
            Connection conn = db.openConnection();
            PersonDAO personDAO = new PersonDAO(conn);
            personDAO.insert(rootPerson);
            personCount += 1;
            EventDAO eDao = new EventDAO(conn);
            eDao.insert(rootBirth);
            eventCount += 1;
            //db.closeConnection(true);
            newestPersons.add(rootPerson);


            Random rand = new Random();
            //for each generation
            ArrayList<Person> latestgen = new ArrayList<>();
            for (int i = 0; i < generations; i++) {

                //for each person in each generation, generate mom & dad, and add their relevant events
                for (int j = 0; j < newestPersons.size(); j++) {
                    //add mother
                    Person mom = new Person(newestPersons.get(j).getMotherID(),
                            userName,
                            fNames.getData().get(rand.nextInt(fNames.getData().size())),
                            sNames.getData().get(rand.nextInt(sNames.getData().size())),
                            "F",
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            newestPersons.get(j).getFatherID());
                    //add father
                    Person dad = new Person(newestPersons.get(j).getFatherID(),
                            userName,
                            mNames.getData().get(rand.nextInt(mNames.getData().size())),
                            newestPersons.get(j).getLastName(),
                            "M",
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            newestPersons.get(j).getMotherID());
                    //last generation shouldn't have parents
                    if (i == generations - 1) {
                        mom.setFatherID(null);
                        mom.setMotherID(null);
                        dad.setFatherID(null);
                        dad.setMotherID(null);
                    }
                    latestgen.add(mom);
                    latestgen.add(dad);
                    personDAO.insert(mom);
                    personDAO.insert(dad);
                    personCount += 2;
                    //create and add events for mom and dad
                    addBirth(mom, nextGenYear, locations.getRandomLocale(), eDao);
                    addDeath(mom, nextGenYear + 30, locations.getRandomLocale(), eDao);
                    addBirth(dad, nextGenYear, locations.getRandomLocale(), eDao);
                    addDeath(dad, nextGenYear + 30, locations.getRandomLocale(), eDao);
                    addMarraige(mom, dad, nextGenYear + 20, locations.getRandomLocale(), eDao);
                    eventCount += 6;
                }
                //set up generation trackers for next gen loop.
                newestPersons.clear();
                newestPersons.addAll(latestgen);
                latestgen.clear();
                nextGenYear = nextGenYear - 25;
            }

            db.closeConnection(true);

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteAllAssociated() throws DataAccessException {
        Database db = new Database();
        PersonDAO pDao = new PersonDAO(db.openConnection());
        pDao.deleteAssociated(userName);
        db.closeConnection(true);
        EventDAO eDao = new EventDAO(db.openConnection());
        eDao.deleteAssociated(userName);
        db.closeConnection(true);
    }

    private void addBirth(Person p, Integer year, LocationPOJO.Locale loc, EventDAO eDao) throws DataAccessException {
        Event bEvent = new Event(UUID.randomUUID().toString(),
                userName,
                p.getPersonID(),
                Double.parseDouble(loc.getLatitude()),
                Double.parseDouble(loc.getLongitude()),
                loc.getCountry(),
                loc.getCity(),
                "Birth",
                year);
        eDao.insert(bEvent);
    }

    private void addDeath(Person p, Integer year, LocationPOJO.Locale loc, EventDAO eDao) throws DataAccessException {
        Event dEvent = new Event(UUID.randomUUID().toString(),
                userName,
                p.getPersonID(),
                Double.parseDouble(loc.getLatitude()),
                Double.parseDouble(loc.getLongitude()),
                loc.getCountry(),
                loc.getCity(),
                "Death",
                year);
        eDao.insert(dEvent);
    }

    private void addMarraige(Person p, Person p2, Integer year, LocationPOJO.Locale loc, EventDAO eDao) throws DataAccessException {
        Event m1Event = new Event(UUID.randomUUID().toString(),
                userName,
                p.getPersonID(),
                Double.parseDouble(loc.getLatitude()),
                Double.parseDouble(loc.getLongitude()),
                loc.getCountry(),
                loc.getCity(),
                "Marriage",
                year);
        eDao.insert(m1Event);
        Event m2Event = new Event(UUID.randomUUID().toString(),
                userName,
                p2.getPersonID(),
                Double.parseDouble(loc.getLatitude()),
                Double.parseDouble(loc.getLongitude()),
                loc.getCountry(),
                loc.getCity(),
                "Marriage",
                year);
        eDao.insert(m2Event);
    }

    private ArrayList<Person> newestPersons = new ArrayList<>();
    private String userName;
    private int generations = 4; //defaults to 4 if no number is passed in.
    private int personCount;
    private int eventCount;

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
