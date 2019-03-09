package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Presence
{
    @JsonProperty("present")
    private boolean isPresent;

    public Presence(boolean isPresent)
    {
        this.isPresent = isPresent;
    }

    public Presence()
    {
        this.isPresent = true;
    }

    public boolean isPresent()
    {
        return isPresent;
    }
}
