package FMSmodels;

public class AuthToken {
    private String tokenID;
    private String userName;

    public AuthToken(String tokenID, String userName) {
        this.tokenID = tokenID;
        this.userName = userName;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
