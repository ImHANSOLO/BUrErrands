package com.example.myapplication.util;

public class User {                          //构造User结构
    private String name;
    private String password;
    private String sex,email,phone,address,headImage;

    public User(String name, String password, String sex, String email, String phone, String address, String headImage){
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSex(){
        return sex;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public String getHeadImage() { return headImage; }

}

