package models.transfer_models;

import models.db_models.Calendar;

public class AddCalendar
{
    Login login;
    Calendar calendar;

    public AddCalendar()
    {
    }

    public AddCalendar(Login login, Calendar calendar)
    {
        this.login = login;
        this.calendar = calendar;
    }

    public Login getLogin()
    {
        return login;
    }

    public void setLogin(Login login)
    {
        this.login = login;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
    }
}
