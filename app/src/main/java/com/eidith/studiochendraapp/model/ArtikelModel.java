package com.eidith.studiochendraapp.model;

import java.util.List;

public class ArtikelModel {

    private int id_artikel;
    private String judul_artikel, deskripsi_artikel, gambar_artikel, video_artikel, tanggal_artikel;

    private int  code;
    private String message;
    private List<ArtikelModel> data_artikel;

    public int getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(int id_artikel) {
        this.id_artikel = id_artikel;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public void setJudul_artikel(String judul_artikel) {
        this.judul_artikel = judul_artikel;
    }

    public String getDeskripsi_artikel() {
        return deskripsi_artikel;
    }

    public void setDeskripsi_artikel(String deskripsi_artikel) {
        this.deskripsi_artikel = deskripsi_artikel;
    }

    public String getGambar_artikel() {
        return gambar_artikel;
    }

    public void setGambar_artikel(String gambar_artikel) {
        this.gambar_artikel = gambar_artikel;
    }

    public String getVideo_artikel() {
        return video_artikel;
    }

    public void setVideo_artikel(String video_artikel) {
        this.video_artikel = video_artikel;
    }

    public String getTanggal_artikel() {
        return tanggal_artikel;
    }

    public void setTanggal_artikel(String tanggal_artikel) {
        this.tanggal_artikel = tanggal_artikel;
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

    public List<ArtikelModel> getData_artikel() {
        return data_artikel;
    }

    public void setData_artikel(List<ArtikelModel> data_artikel) {
        this.data_artikel = data_artikel;
    }
}
