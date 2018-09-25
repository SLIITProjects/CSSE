package com.example.csse.csse.Model;

public class User {
    private String email,fname,dob,nic,password,mobile;

    public User() {
    }

    public User(String email, String fname, String dob, String nic, String password, String mobile) {
        this.email = email;
        this.fname = fname;
        this.dob = dob;
        this.nic = nic;
        this.password = password;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
