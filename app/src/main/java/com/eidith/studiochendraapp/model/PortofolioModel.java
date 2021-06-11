package com.eidith.studiochendraapp.model;

import java.util.List;

public class PortofolioModel {

    private int id_portofolio;
    private String judul_portofolio, deskripsi_foto, gambar_foto;

    private int  code;
    private String message;
    private List<PortofolioModel> data_portofolio;

    public int getId_portofolio() {
        return id_portofolio;
    }

    public void setId_portofolio(int id_portofolio) {
        this.id_portofolio = id_portofolio;
    }

    public String getJudul_portofolio() {
        return judul_portofolio;
    }

    public void setJudul_portofolio(String judul_portofolio) {
        this.judul_portofolio = judul_portofolio;
    }

    public String getDeskripsi_foto() {
        return deskripsi_foto;
    }

    public void setDeskripsi_foto(String deskripsi_foto) {
        this.deskripsi_foto = deskripsi_foto;
    }

    public String getGambar_foto() {
        return gambar_foto;
    }

    public void setGambar_foto(String gambar_foto) {
        this.gambar_foto = gambar_foto;
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

    public List<PortofolioModel> getData_portofolio() {
        return data_portofolio;
    }

    public void setData_portofolio(List<PortofolioModel> data_portofolio) {
        this.data_portofolio = data_portofolio;
    }
}
