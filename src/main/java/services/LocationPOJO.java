package services;

import java.util.ArrayList;
import java.util.Random;

public class LocationPOJO {
    private ArrayList<Locale> data;

    public ArrayList<Locale> getData() {
        return data;
    }

    public void setData(ArrayList<Locale> data) {
        this.data = data;
    }

    public Locale getRandomLocale() {
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }

    public class Locale {
        private String country;
        private String city;
        private String latitude;
        private String longitude;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
