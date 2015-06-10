package com.example.steve.tessuitmediaandroid.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PersonGuestModel extends RealmObject {

    @PrimaryKey
    private String id;

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
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The name
     */
    public void setId (String id) {
        this.id = id;
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