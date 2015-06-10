package com.example.steve.tessuitmediaandroid.model;


import io.realm.RealmObject;

public class PersonGuestModel extends RealmObject {

    private String name;

    private String birthdate;

    public PersonGuestModel() {

    }

    public PersonGuestModel (String name, String birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }
    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     *
     * @param birthdate
     * The birthdate
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

}