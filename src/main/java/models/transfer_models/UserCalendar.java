package models.transfer_models;

import models.db_models.Calendar;
import models.db_models.Event;
import models.db_models.Label;

import java.util.ArrayList;

public class UserCalendar
{
    private Calendar calendar;
    private ArrayList<Label> labels;
    private ArrayList<Event> events;

    public UserCalendar(){}

    public UserCalendar(Calendar calendar, ArrayList<Label> labels, ArrayList<Event> events)
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
