package models.db_models;

import org.joda.time.DateTime;

public class Event
{
    private Long id;
    private String name;
    private String comment;

    private DateTime startDate;
    private DateTime endDate;

    private Long label_id;
    private Long calendar_id;

    public Event(){}

    public Event(Long id, String name, String comment, DateTime startDate, DateTime endDate, Long label, Long calendar)
    {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.label_id = label;
        this.calendar_id = calendar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Long getLabel_id() {
        return label_id;
    }

    public void setLabel_id(Long label_id) {
        this.label_id = label_id;
    }

    public Long getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(Long calendar_id) {
        this.calendar_id = calendar_id;
    }
}
