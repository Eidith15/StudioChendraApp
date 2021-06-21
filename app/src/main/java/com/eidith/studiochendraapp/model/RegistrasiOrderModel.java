package com.eidith.studiochendraapp.model;

import java.util.List;

public class RegistrasiOrderModel {

    private int id_registrasi, id_layanan, id_user;
    private String tanggal_registrasi, judul_layanan, gambar_layanan, tanggal_layanan, nama_user, email_user, no_handphone_user;

    private int code;
    private String message;
    private List<RegistrasiOrderModel> data_registrasi;

    public int getId_registrasi() {
        return id_registrasi;
    }

    public void setId_registrasi(int id_registrasi) {
        this.id_registrasi = id_registrasi;
    }

    public int getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(int id_layanan) {
        this.id_layanan = id_layanan;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(String tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }

    public String getJudul_layanan() {
        return judul_layanan;
    }

    public void setJudul_layanan(String judul_layanan) {
        this.judul_layanan = judul_layanan;
    }

    public String getGambar_layanan() {
        return gambar_layanan;
    }

    public void setGambar_layanan(String gambar_layanan) {
        this.gambar_layanan = gambar_layanan;
    }

    public String getTanggal_layanan() {
        return tanggal_layanan;
    }

    public void setTanggal_layanan(String tanggal_layanan) {
        this.tanggal_layanan = tanggal_layanan;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RegistrasiOrderModel> getData_registrasi() {
        return data_registrasi;
    }

    public void setData_registrasi(List<RegistrasiOrderModel> data_registrasi) {
        this.data_registrasi = data_registrasi;
    }
}
