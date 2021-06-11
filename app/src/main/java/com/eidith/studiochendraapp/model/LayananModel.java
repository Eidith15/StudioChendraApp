package com.eidith.studiochendraapp.model;

import java.util.List;

public class LayananModel {

    private int id_layanan;
    private String judul_layanan, deskripsi_layanan, gambar_layanan, video_layanan,tanggal_layanan;

    private int  code;
    private String message;
    private List<LayananModel> data_layanan;

    public int getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(int id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getJudul_layanan() {
        return judul_layanan;
    }

    public void setJudul_layanan(String judul_layanan) {
        this.judul_layanan = judul_layanan;
    }

    public String getDeskripsi_layanan() {
        return deskripsi_layanan;
    }

    public void setDeskripsi_layanan(String deskripsi_layanan) {
        this.deskripsi_layanan = deskripsi_layanan;
    }

    public String getGambar_layanan() {
        return gambar_layanan;
    }

    public void setGambar_layanan(String gambar_layanan) {
        this.gambar_layanan = gambar_layanan;
    }

    public String getVideo_layanan() {
        return video_layanan;
    }

    public void setVideo_layanan(String video_layanan) {
        this.video_layanan = video_layanan;
    }

    public String getTanggal_layanan() {
        return tanggal_layanan;
    }

    public void setTanggal_layanan(String tanggal_layanan) {
        this.tanggal_layanan = tanggal_layanan;
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

    public List<LayananModel> getData_layanan() {
        return data_layanan;
    }

    public void setData_layanan(List<LayananModel> data_layanan) {
        this.data_layanan = data_layanan;
    }
}
