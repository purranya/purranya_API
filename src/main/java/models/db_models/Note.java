package models.db_models;

import static utils.ValidationUtils.length;
import static utils.ValidationUtils.name;

public class Note
{
    private Long id;
    private String name;
    private String comment;
    private Long user_id;


    public Note(){}

    public Note(Long id, String name, String comment, Long user_id)
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

    public boolean isValid()
    {
        return isNameValid() &&
                isCommentValid() &&
                isUserIdValid();
    }

    public boolean isNameValid() {
        return (name != null &&
                length(name, 1, 50) &&
                name(name));
    }

    public boolean isCommentValid() {
        return (comment != null &&
                length(name, 0, 255));
    }

    public boolean isUserIdValid()
    {
        return user_id != null && user_id > 0;
    }
}
