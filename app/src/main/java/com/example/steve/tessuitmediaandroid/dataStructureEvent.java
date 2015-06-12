package com.example.steve.tessuitmediaandroid;


/**
 * Created by steve on 20/03/2015.
 */


public class dataStructureEvent {
    private String nama;
    private String tanggal;

    public dataStructureEvent(String nama, String tanggal) {
        this.nama = nama;
        this.tanggal = tanggal;
    }
    public String getNama() {
        return nama;
    }
    public String getTanggal() {
        return tanggal;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}