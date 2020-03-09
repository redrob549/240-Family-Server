package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import responses.AuthTokenResponse;
import responses.Response;

import java.io.IOException;
public class Serializer {
    public String generateResponse(Response resp) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(resp);

        //System.out.println(jsonString);
        return  jsonString;
    }
    public String generateAuthTokenResponse(AuthTokenResponse resp) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(resp);

        //System.out.println(jsonString);
        return  jsonString;
    }
}
