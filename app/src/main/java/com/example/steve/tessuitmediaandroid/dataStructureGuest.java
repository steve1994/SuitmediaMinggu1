package com.example.steve.tessuitmediaandroid;

/**
 * Created by steve on 20/03/2015.
 */
public class dataStructureGuest {
    private String nama;
    private String birthday;

    public dataStructureGuest(String nama, String birthday) {
        this.nama = nama;
        this.birthday = birthday;
    }
    public String getNama() {
        return nama;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setBirthday(String tanggal) {
        this.birthday = tanggal;
    }
}
