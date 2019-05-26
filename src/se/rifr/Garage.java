package se.rifr;

public class Garage implements java.io.Serializable{

    private String name;
    private String description;
    private double feePerHour;

    public Garage(String name, String description, double feePerHour) {
        this.name = name;
        this.description = description;
        this.feePerHour = feePerHour;
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

    public double getFeePerHour() {
        return feePerHour;
    }

    public void setFeePerHour(double feePerHour) {
        this.feePerHour = feePerHour;
    }

    public double getFeePerMinute() {
        return feePerHour / 60.0d;
    }

    @Override
    public String toString() {
        return "Garage{Name=" + Str.padRight(getName(),30) +
                ", Cost/Hour="+Str.padRight(Double.toString(getFeePerHour()),10) +
                ", Description='" + description + '\'' +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Name",30);
        returnString += Str.padRight("Cost/Hour",10);
        returnString += Str.padRight("Description",30);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getName(),30);
        returnString  = Str.padRight(Double.toString(getFeePerHour()),10);
        returnString += Str.padRight(getDescription(),30);
        return returnString;
    }

}
