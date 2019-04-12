package models.db_models;

import static utils.ValidationUtils.length;
import static utils.ValidationUtils.name;

public class Label
{
    private Long id;
    private String name;
    private short color_r;
    private short color_b;
    private short color_g;
    private Long calendar_id;

    public Label() {}

    public Label(Long id, String name, short color_r, short color_b, short color_g, Long calendar_id) {
        this.id = id;
        this.name = name;
        this.color_r = color_r;
        this.color_b = color_b;
        this.color_g = color_g;
        this.calendar_id = calendar_id;
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

    public short getColor_r() {
        return color_r;
    }

    public void setColor_r(short color_r) {
        this.color_r = color_r;
    }

    public short getColor_b() {
        return color_b;
    }

    public void setColor_b(short color_b) {
        this.color_b = color_b;
    }

    public short getColor_g() {
        return color_g;
    }

    public void setColor_g(short color_g) {
        this.color_g = color_g;
    }

    public Long getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(Long calendar_id) {
        this.calendar_id = calendar_id;
    }


    public boolean isValid()
    {
        return isNameValid() &&
                isColorRValid() &&
                isColorGValid() &&
                isColorBValid() &&
                isCalendarIdValid();
    }

    public boolean isNameValid() {
        return (name != null &&
                length(name, 1, 50) &&
                name(name));
    }

    public boolean isColorRValid() {
        return (color_r >= 0 && color_r <= 255);
    }

    public boolean isColorBValid() {
        return (color_b >= 0 && color_b <= 255);
    }

    public boolean isColorGValid() {
        return (color_g >= 0 && color_g <= 255);
    }

    public boolean isCalendarIdValid()
    {
        return calendar_id != null && calendar_id > 0;
    }


}
