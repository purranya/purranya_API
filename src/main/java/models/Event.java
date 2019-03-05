package models;

import org.joda.time.DateTime;

public class Event
{
    private Long id;
    private String name;
    private String comment;

    private DateTime startDate;
    private DateTime endDate;

    private boolean isArchived;
    private Label label;

    public Event(){}

    public Event(Long id, String name, String comment, DateTime startDate, DateTime endDate, boolean isArchived, Label label)
    {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isArchived = isArchived;
        this.label = label;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public DateTime getStartDate()
    {
        return startDate;
    }

    public void setStartDate(DateTime startDate)
    {
        this.startDate = startDate;
    }

    public DateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(DateTime endDate)
    {
        this.endDate = endDate;
    }

    public boolean isArchived()
    {
        return isArchived;
    }

    public void setArchived(boolean archived)
    {
        isArchived = archived;
    }

    public Label getLabel()
    {
        return label;
    }

    public void setLabel(Label label)
    {
        this.label = label;
    }
}
