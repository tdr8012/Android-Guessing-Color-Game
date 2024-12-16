package com.example.thaiduyroan_finalexam;

public class User {
    public String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
    public String password;
    public int score;

    public User() {
    }

    public User(String username, String name, String password, int score) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.score = score;
    }


}
