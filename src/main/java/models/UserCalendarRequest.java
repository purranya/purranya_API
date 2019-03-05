package models;

import java.util.ArrayList;

public class UserCalendarRequest
{
    private Calendar calendar;
    private ArrayList<Label> labels;
    private ArrayList<Event> events;

    public UserCalendarRequest(){}

    public UserCalendarRequest(Calendar calendar, ArrayList<Label> labels, ArrayList<Event> events)
    {
        this.calendar = calendar;
        this.labels = labels;
        this.events = events;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
    }

    public ArrayList<Label> getLabels()
    {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels)
    {
        this.labels = labels;
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public void setEvents(ArrayList<Event> events)
    {
        this.events = events;
    }
}
