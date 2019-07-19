package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import java.util.Objects;

//@Entity
//@Table(name = "Users")
public class User implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

//    @Column(name = "firstName",nullable = false)
    private String firstName;

//    @Column(name = "lastName",nullable = false)
    private String lastName;

//    @Column(name = "barCode",nullable = false)
    private String barcode;

//    @Column(name = "email")
    private String email;

//    @Id
//    @Column(name = "userName",unique = true,nullable = false)
    private String userName;

//    @Column(name = "password",nullable = false)
    private String password;


    public User (User.Builder builder) {

        this.firstName = Objects.requireNonNull(builder.firstName);
        this.lastName  = Objects.requireNonNull(builder.lastName);
        this.barcode   = Objects.requireNonNull(builder.barcode);
        this.userName  = Objects.requireNonNull(builder.userName);
        this.password  = Objects.requireNonNull(builder.password);
        this.email     = builder.email;

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

    public static Builder builder(){ return new Builder();}

    public static class Builder {

        private String firstName;
        private String lastName;
        private String barcode;
        private String email;
        private String userName;
        private String password;

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withBarCode(String barcode){
            this.barcode = barcode;
            return this;
        }

        public Builder withemail(String email){
            this.email = email;
            return this;
        }

        public Builder withUserName(String userName){
            this.userName = userName;
            return this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

}
