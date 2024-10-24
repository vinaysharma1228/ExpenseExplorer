package com.example.expenseexplorer;

public class user {
    String username,name,profileImage,password,depositAmt,remainAmt,totalExp;


    public user() {
    }

    public user(String username, String name, String profileImage, String password, String depositAmt, String remainAmt, String totalExp) {
        this.username = username;
        this.name = name;
        this.profileImage = profileImage;
        this.password = password;
        this.depositAmt = depositAmt;
        this.remainAmt = remainAmt;
        this.totalExp = totalExp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(String depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(String remainAmt) {
        this.remainAmt = remainAmt;
    }

    public String getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(String totalExp) {
        this.totalExp = totalExp;
    }
}
