package models.db_models;

public class Calendar
{
    private Long id;
    private String name;
    private String comment;
    private Long user_id;

    public Calendar()
    {
    }

    public Calendar(Long id, String name, String comment)
    {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public Calendar(Long id, String name, String comment, Long user_id)
    {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.user_id = user_id;
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

    public Long getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Long user_id)
    {
        this.user_id = user_id;
    }

    public boolean validateName() {
        return (name.length() > 2 &&
            name.length() < 50 &&
            name != null &&
            !name.equals("") &&
            name.matches("^[-a-zA-Z0-9_]+$"));
    }

    public boolean validateComment() {
        return (comment.length() > 256 &&
            comment != null);
    }
}
