package se.rifr;

public class User  implements java.io.Serializable{

    private String firstName;
    private String lastName;
    private String barcode;
    private String email;
    private String userName;
    private String password;

    public User(String firstName, String lastName, String barcode, String email, String userName, String password) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.barcode   = barcode;
        this.email     = email;
        this.userName  = userName;
        this.password  = password;
    }

    public String getKey() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", barcode='" + barcode + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Firstname",16);
        returnString += Str.padRight("Lastname",16);
        returnString += Str.padRight("Barcode",14);
        returnString += Str.padRight("email",40);
        returnString += Str.padRight("Username",20);
        returnString += Str.padRight("Password",20);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getFirstName(),16);
        returnString += Str.padRight(getLastName(),16);
        returnString += Str.padRight(getBarcode(),14);
        returnString += Str.padRight(getEmail(),40);
        returnString += Str.padRight(getUserName(),20);
        returnString += Str.padRight(getPassword(),20);
        return returnString;
    }

}
