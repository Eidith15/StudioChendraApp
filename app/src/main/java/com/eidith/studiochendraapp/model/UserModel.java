package com.eidith.studiochendraapp.model;


public class UserModel {
    private int id_user, access_code;
    private String nama_user, email_user, no_handphone_user, username_user, password_user;

    public UserModel(int id_user, String nama_user, String email_user, String no_handphone_user, String username_user, String password_user, int access_code) {
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.email_user = email_user;
        this.no_handphone_user = no_handphone_user;
        this.username_user = username_user;
        this.password_user = password_user;
        this.access_code = access_code;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getAccess_code() {
        return access_code;
    }

    public void setAccess_code(int access_code) {
        this.access_code = access_code;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getNo_handphone_user() {
        return no_handphone_user;
    }

    public void setNo_handphone_user(String no_handphone_user) {
        this.no_handphone_user = no_handphone_user;
    }

    public String getUsername_user() {
        return username_user;
    }

    public void setUsername_user(String username_user) {
        this.username_user = username_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }
}
