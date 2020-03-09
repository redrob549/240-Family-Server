package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import FMSmodels.User;
import requests.LoadRequest;
import services.LocationPOJO;
import services.NamePOJO;

import java.io.*;

public class Deserializer {

    private void printUsage() {
        System.out.println("USAGE: java Deserializer inputFilePath");
    }

    public User parseUser(InputStream is) throws IOException {
        try {
            String json = readString(is);
            Gson gson = new Gson();
            User user = gson.fromJson(json, User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoadRequest parseLoadRequest(InputStream is) throws IOException {
        try {
            String json = readString(is);
            Gson gson = new Gson();
            LoadRequest lr = gson.fromJson(json, LoadRequest.class);
            return lr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NamePOJO parseNameList(File file) throws IOException {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            NamePOJO ret = gson.fromJson(bufferedReader, NamePOJO.class);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocationPOJO parseLocationList(File file) throws IOException {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            LocationPOJO ret = gson.fromJson(bufferedReader, LocationPOJO.class);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
