package models.db_models;

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
}
