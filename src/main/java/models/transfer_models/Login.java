package models.transfer_models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.ValidationUtils;

public class Login
{

    String username;
    String password;

    public Login(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public Login(){}

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @JsonIgnore
    public boolean isValid()
    {
        return isUsernameValid() &&
                isPasswordValid();
    }

    @JsonIgnore
    public boolean isUsernameValid()
    {
        return username != null && ValidationUtils.length(username,2,32) && ValidationUtils.name(username);
    }

    @JsonIgnore
    public boolean isPasswordValid()
    {
        return password != null && ValidationUtils.length(password,8,32) ;
    }
}

