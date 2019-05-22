package se.rifr;

public class Garage implements java.io.Serializable{

    String name;
    String description;

    public Garage(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getKey() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Garage{Name=" + Str.padRight(getName(),30) +
                ", description='" + description + '\'' +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Name",16);
        returnString += Str.padRight("Description",30);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getName(),16);
        returnString += Str.padRight(getDescription(),30);
        return returnString;
    }

}
