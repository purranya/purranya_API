package models.db_models;

import static utils.ValidationUtils.length;
import static utils.ValidationUtils.name;

public class Label
{
    private Long id;
    private String name;
    private int color_r;
    private int color_b;
    private int color_g;
    private Long calendar_id;

    public Label() {}

    public Label(Long id, String name, int color_r, int color_b, int color_g, Long calendar_id) {
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

    public int getColor_r() {
        return color_r;
    }

    public void setColor_r(int color_r) {
        this.color_r = color_r;
    }

    public int getColor_b() {
        return color_b;
    }

    public void setColor_b(int color_b) {
        this.color_b = color_b;
    }

    public int getColor_g() {
        return color_g;
    }

    public void setColor_g(int color_g) {
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
                isColorValid() &&
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

    public boolean isColorValid() { return (isColorRValid() && isColorGValid() && isColorBValid()); }

    public boolean isCalendarIdValid()
    {
        return calendar_id != null && calendar_id > 0;
    }


}
