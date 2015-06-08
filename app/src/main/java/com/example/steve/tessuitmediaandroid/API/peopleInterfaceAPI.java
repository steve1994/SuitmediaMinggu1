package com.example.steve.tessuitmediaandroid.API;

import com.example.steve.tessuitmediaandroid.model.ListPersonGuestModel;
import com.example.steve.tessuitmediaandroid.model.PersonGuestModel;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by steve on 06/06/2015.
 */

public interface peopleInterfaceAPI {

    @GET("/people")
    void getPeople(Callback<Response> callback);
}
