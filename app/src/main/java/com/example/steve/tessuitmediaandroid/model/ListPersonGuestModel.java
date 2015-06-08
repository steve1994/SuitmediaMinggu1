package com.example.steve.tessuitmediaandroid.model;

import java.util.List;

/**
 * Created by steve on 06/06/2015.
 */
public class ListPersonGuestModel {
    private List<PersonGuestModel> listPeople;

    public List<PersonGuestModel> getListPeople() {
        return listPeople;
    }

    public int getListPeopleSize() {
        return listPeople.size();
    }

    public PersonGuestModel getPeople(int index) {
        return listPeople.get(index);
    }

}
