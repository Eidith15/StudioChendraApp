package com.eidith.studiochendraapp.model;

import java.util.List;

public class WorkshopModel {
    private int id_workshop;
    private String judul_workshop, deskripsi_workshop, gambar_workshop, video_workshop;
    private String tanggal_workshop;

    private int  code;
    private String message;
    private List<WorkshopModel> data_workshop;

    public String getTanggal_workshop() {
        return tanggal_workshop;
    }

    public void setTanggal_workshop(String tanggal_workshop) {
        this.tanggal_workshop = tanggal_workshop;
    }

    public int getId_workshop() {
        return id_workshop;
    }

    public void setId_workshop(int id_workshop) {
        this.id_workshop = id_workshop;
    }

    public String getJudul_workshop() {
        return judul_workshop;
    }

    public void setJudul_workshop(String judul_workshop) {
        this.judul_workshop = judul_workshop;
    }

    public String getDeskripsi_workshop() {
        return deskripsi_workshop;
    }

    public void setDeskripsi_workshop(String deskripsi_workshop) {
        this.deskripsi_workshop = deskripsi_workshop;
    }

    public String getGambar_workshop() {
        return gambar_workshop;
    }

    public void setGambar_workshop(String gambar_workshop) {
        this.gambar_workshop = gambar_workshop;
    }

    public String getVideo_workshop() {
        return video_workshop;
    }

    public void setVideo_workshop(String video_workshop) {
        this.video_workshop = video_workshop;
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

    public List<WorkshopModel> getData_workshop() {
        return data_workshop;
    }

    public void setData_workshop(List<WorkshopModel> data_workshop) {
        this.data_workshop = data_workshop;
    }
}
