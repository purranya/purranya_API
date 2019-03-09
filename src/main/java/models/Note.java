package models;

public class Note
{
    private Long id;
    private String name;
    private String comment;


    public Note(){}

    public Note(Long id, String name, String comment)
    {
        this.id = id;
        this.name = name;
        this.comment = comment;
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
}
