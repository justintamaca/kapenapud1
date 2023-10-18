package com.example.kapenapud1;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private String user_id;
    private String first_name;
    private String last_name;
    private String gender;
    private String birthday;
    private String contact_no;
    private String avatar;
    private String rewards;
    private String email;


    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getRewards() {
        return rewards;
    }

    public String getEmail() {
        return email;
    }
}
