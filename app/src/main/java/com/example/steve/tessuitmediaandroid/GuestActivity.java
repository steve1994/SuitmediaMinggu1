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

@EActivity(R.layout.activity_guest)

public class GuestActivity extends Activity {
    rowGuestList adapter;
    @ViewById GridView listGuest;
    private ArrayList<dataStructureGuest> namaBirthdateGuest;
    private String namePeopleSelected = "anonim";
    private String birthdayPeopleSelected = "31-12-1999";
    private RestAdapter restAdapter;

    // PRIMITIF UNTUK PARSE JSON STRING
    private void getListGuestJSON (String response) {
        // Parse string response dalam format json, masukkan ke list vector listGuest
        JSONArray guest = null;
        try {
            guest = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert guest != null;
        for (int i=0; i<guest.length(); i++) {
            JSONObject itemGuest = null;
            try {
                itemGuest = guest.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String nama = "";
            try {
                assert itemGuest != null;
                nama = itemGuest.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String birthday = "";
            try {
                birthday = itemGuest.getString("birthdate");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            namaBirthdateGuest.add(new dataStructureGuest(nama, birthday));
            //tempRequestResult.add(i, new dataStructureGuest(nama, birthday));
            //adapter.add(new dataStructureGuest(nama, birthday));
        }
    }

    @AfterViews
    void setGridView() {
        namaBirthdateGuest = new ArrayList<>();
        fetchPeopleFromAPI("http://dry-sierra-6832.herokuapp.com/api");
    }

    @Background
    void fetchPeopleFromAPI (String urlEndpoint) {
       /* // Buat objek http dan url
        URL endpoint = null;
        try {
            endpoint = new URL(urlEndpoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection askEndpoint = null;
        try {
            assert endpoint != null;
            askEndpoint = (HttpURLConnection) endpoint.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Baca response dari request endpoint dalam bentuk buffer stream
        BufferedReader reader = null;
        try {
            assert askEndpoint != null;
            reader = new BufferedReader(new InputStreamReader(askEndpoint.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ubah stream buffer dari response ke string
        // String response doInBackground
        String response = "";
        String inputLineReader;
        try {
            assert reader != null;
            while ((inputLineReader = reader.readLine()) != null) {
                response += inputLineReader;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tutup koneksi endpoint
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Lakukan pemanggilan REST API melalui restAdapter
        restAdapter = new RestAdapter.Builder().setEndpoint(urlEndpoint).build();
        peopleInterfaceAPI peopleAPI = restAdapter.create(peopleInterfaceAPI.class);
        peopleAPI.getPeople(new Callback<List<PersonGuestModel>>() {
            @Override
            public void success(List<PersonGuestModel> personGuestModels, Response response) {
                for (PersonGuestModel person : personGuestModels) {
                    Log.d("name", person.getName());
                    Log.d("birthdate", person.getBirthdate());
                    namaBirthdateGuest.add (new dataStructureGuest (person.getName(), person.getBirthdate()));
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
        // Tambahkan response API ke adapter
       /* for (PersonGuestModel response : responses) {
            Log.d("name", response.getName());
            Log.d("birthdate", response.getBirthdate());
            namaBirthdateGuest.add (new dataStructureGuest (response.getName(), response.getBirthdate()));
        }*/
        // Kirim hasil parsing dalam bentuk model ke UIThread
        setGridViewAdapter();
    }

    @UiThread
    void setGridViewAdapter() {
        /*// Parsing string json, masukkan ke arraylist adapter
        getListGuestJSON(response);*/

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
