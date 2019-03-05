package models;

import java.util.ArrayList;

public class UserCalendarIndex
{
    private ArrayList<Calendar> calendars;

    public UserCalendarIndex(ArrayList<Calendar> calendars)
    {
        this.calendars = calendars;
    }

    public ArrayList<Calendar> getCalendars()
    {
        return calendars;
    }

    public void setCalendars(ArrayList<Calendar> calendars)
    {
        this.calendars = calendars;
    }
}
