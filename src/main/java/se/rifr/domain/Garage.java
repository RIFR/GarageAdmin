package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import java.util.Objects;

//@Entity
//@Table(name = "Garages")
public class Garage implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

//    @Id
//    @Column(name = "name",unique = true,nullable = false)
    private String name;

//    @Column(name = "description")
    private String description;

    public Garage(String name, String description) {
        this.name = Objects.requireNonNull(name);
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
                ", Description='" + description + '\'' +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Name",30);
        returnString += Str.padRight("Description",30);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getName(),30);
        returnString += Str.padRight(getDescription(),30);
        return returnString;
    }

}
