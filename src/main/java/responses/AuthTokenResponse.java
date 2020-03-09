package responses;

import FMSmodels.AuthToken;

public class AuthTokenResponse extends Response {
    private String authToken;
    private String userName;
    private String personID;

    public AuthTokenResponse(AuthToken myAuthToken, String ID) {
        authToken = myAuthToken.getTokenID();
        userName = myAuthToken.getUserName();
        personID = ID;
        setSuccess(true);
    }

}
