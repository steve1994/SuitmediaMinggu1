package com.example.steve.tessuitmediaandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.steve.tessuitmediaandroid.API.peopleInterfaceAPI;
import com.example.steve.tessuitmediaandroid.model.ListPersonGuestModel;
import com.example.steve.tessuitmediaandroid.model.PersonGuestModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

@EActivity(R.layout.activity_guest)

public class GuestActivity extends Activity {
    rowGuestList adapter;
    @ViewById GridView listGuest;
    private ArrayList<dataStructureGuest> namaBirthdateGuest;
    private String namePeopleSelected = "anonim";
    private String birthdayPeopleSelected = "31-12-1999";
    private RestAdapter restAdapter;

    @AfterViews
    void setGridView() {
        namaBirthdateGuest = new ArrayList<>();
        fetchPeopleFromAPI("http://dry-sierra-6832.herokuapp.com/api");
    }

    @Background
    void fetchPeopleFromAPI (String urlEndpoint) {
        // Lakukan pemanggilan REST API melalui restAdapter
        restAdapter = new RestAdapter.Builder().setEndpoint(urlEndpoint).build();
        peopleInterfaceAPI peopleAPI = restAdapter.create(peopleInterfaceAPI.class);
        // Handle response API
        peopleAPI.getPeople(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                // Dapatkan string json array dari http response
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String lineBuffer;
                    while ((lineBuffer = reader.readLine()) != null) {
                        sb.append(lineBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = sb.toString();

                // Konversi string json array ke bentuk array of parcelable object (POJO Model)
                Gson gson = new Gson();
                PersonGuestModel[] resultJSONArray = gson.fromJson(result, PersonGuestModel[].class);

                // Tambahkan masing-masing array of parcelable object (POJO Model) ke adapter
                for (PersonGuestModel people : resultJSONArray) {
                    adapter.add (new dataStructureGuest (people.getName(), people.getBirthdate()));
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("SUITMEDIA", "gagal " + retrofitError.getMessage());
            }
        });

        // Kirim hasil parsing dalam bentuk model ke UIThread
        setGridViewAdapter();
    }

    @UiThread
    void setGridViewAdapter() {
        // Bentuk kelas adapter beserta memasangnya ke gridview list
        adapter = new rowGuestList(this, namaBirthdateGuest);
        listGuest.setAdapter(adapter);
    }

    @ItemClick(R.id.listGuest)
    void guestListItemClicked (dataStructureGuest guest) {
        namePeopleSelected = guest.getNama();
        birthdayPeopleSelected = guest.getBirthday();
        finish();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("namaGuest", namePeopleSelected);
        intent.putExtra("birthdayGuest", birthdayPeopleSelected);
        setResult(RESULT_OK, intent);
        // adapter.clear();
        super.finish();
    }
}
