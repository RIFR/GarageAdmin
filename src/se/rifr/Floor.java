package se.rifr;
public class Floor implements java.io.Serializable {

    private int    level;
    private String Description;

    public Floor(int level, String description) {
        this.level = level;
        Description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
