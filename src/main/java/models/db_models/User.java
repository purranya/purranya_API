package models.db_models;

import utils.ValidationUtils;

public class User
{
    private Long id;
    private String username;
    private String password_hash;

    public User(Long id, String username, String password_hash)
    {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword_hash()
    {
        return password_hash;
    }

    public void setPassword_hash(String password_hash)
    {
        this.password_hash = password_hash;
    }

    public boolean isValid()
    {
        return isUsernameValid() &&
                isPasswordValid();
    }

    public boolean isUsernameValid()
    {
        return username != null && ValidationUtils.length(username,2,32) && ValidationUtils.name(username);
    }

    public boolean isPasswordValid()
    {
        return password_hash != null && password_hash.length() == 64;
    }
}
