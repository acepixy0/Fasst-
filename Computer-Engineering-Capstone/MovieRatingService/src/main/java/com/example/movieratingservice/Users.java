package com.example.movieratingservice;
//This is a file
public class Users {

    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double balance;
    //Default Constructor
    public Users(){
        this.uuid = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.balance = 0.0;
    }
    //Parametric Constructor
    public Users(String first, String last, String email, String pass, double balance){
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = pass;
        this.balance = balance;

    }
    //Copy Constructor
    public Users(Users copy){
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.email = copy.email;
        this.password = copy.password;
        this.balance = copy.balance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Users{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
