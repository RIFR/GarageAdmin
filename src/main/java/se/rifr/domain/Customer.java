package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

import java.util.Objects;

public class Customer implements java.io.Serializable{

    private String firstName;
    private String lastName;
    private String barCode;
    private String email;
    private String telephoneNumber;
    private String userName;

    public Customer(Customer.Builder builder) {

        this.firstName       = Objects.requireNonNull(builder.firstName);
        this.lastName        = Objects.requireNonNull(builder.lastName);
        this.barCode         = Objects.requireNonNull(builder.barCode);
        this.email           = builder.email;
        this.telephoneNumber = builder.telephoneNumber;
        this.userName        = builder.userName;

    }

    public String getKey() {
        return barCode;
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

    public String getFullName() {
        return firstName + " " + lastName;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNo='" + telephoneNumber + '\'' +
                ", barCode='" + barCode + '\'' +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Firstname",16);
        returnString += Str.padRight("Lastname",16);
        returnString += Str.padRight("Barcode",14);
        returnString += Str.padRight("email",40);
        returnString += Str.padRight("telephoneNo",20);
        returnString += Str.padRight("Username",20);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getFirstName(),16);
        returnString += Str.padRight(getLastName(),16);
        returnString += Str.padRight(getBarCode(),14);
        returnString += Str.padRight(getEmail(),40);
        returnString += Str.padRight(getTelephoneNumber(),20);
        returnString += Str.padRight(getUserName(),20);
        return returnString;
    }

    public static Builder builder(){ return new Builder();}

    public static class Builder {

        private String firstName;
        private String lastName;
        private String barCode;
        private String email;
        private String telephoneNumber;
        private String userName;

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withBarCode(String barCode){
            this.barCode = barCode;
            return this;
        }

        public Builder withemail(String email){
            this.email = email;
            return this;
        }

        public Builder withTelephoneNumber(String telephoneNumber){
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public Builder withUserName(String userName){
            this.userName = userName;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }
}
