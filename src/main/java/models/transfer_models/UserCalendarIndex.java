package models.transfer_models;

import models.db_models.Calendar;

import java.util.ArrayList;

public class UserCalendarIndex
{
    private ArrayList<Calendar> calendars;

    public UserCalendarIndex(ArrayList<Calendar> calendars)
    {
        this.calendars = calendars;
    }

    public UserCalendarIndex(){}

    public ArrayList<Calendar> getCalendars()
    {
        return calendars;
    }

    public void setCalendars(ArrayList<Calendar> calendars)
    {
        this.calendars = calendars;
    }
}
