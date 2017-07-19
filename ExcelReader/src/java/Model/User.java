/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Ritesh
 */
public class User {
    private double id;
    private String username;
    private String first_name;
    private String last_name;
    private String address;
    private double age;
    private double passing_year;

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws Exception {
        validateLength(username);
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) throws Exception {
        validateLength(first_name);
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) throws Exception {
        validateLength(last_name);
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws Exception {
        validateLength(address);
        this.address = address;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) throws Exception {
        validateAge(age);
        this.age = age;
    }

    public double getPassing_year() {
        return passing_year;
    }

    public void setPassing_year(double passing_year) throws Exception {
        validateYear(passing_year);
        this.passing_year = passing_year;
    }
    
    private void validateAge(double age) throws Exception {
        if(age < 18)
            throw new Exception("Age should be greater than 18 years");
    }
    
    private void validateYear(double year) throws Exception {
        System.out.println("d " + year);
        if(year < 2014 || year > 2017)
            throw new Exception("Year should be between 2014 and 2017");
    }
    
    private void validateLength(String data) throws Exception {
        if(data.length() == 0)
            throw new Exception("Please proovide some data");
    }
}
