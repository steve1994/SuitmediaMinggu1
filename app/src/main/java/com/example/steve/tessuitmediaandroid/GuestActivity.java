package com.example.steve.tessuitmediaandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.steve.tessuitmediaandroid.API.peopleInterfaceAPI;
import com.example.steve.tessuitmediaandroid.model.ListPersonGuestModel;
import com.example.steve.tessuitmediaandroid.model.PersonGuestModel;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

@EFragment (R.layout.activity_guest)

public class GuestActivity extends Fragment {
    private rowGuestList adapter;
    private Realm realm;
    @ViewById GridView listGuest;
    private ArrayList<dataStructureGuest> namaBirthdateGuest = new ArrayList<>();
    private String namePeopleSelected = "anonim";
    private String birthdayPeopleSelected = "31-12-1999";
    private RestAdapter restAdapter;
    private Activity activity;
    private static final String URL = "http://dry-sierra-6832.herokuapp.com/api";

    public interface onGuestItemListClickListener {
        public void onGuestItemListClick(dataStructureGuest guest);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @AfterViews
    public void setGridView() {
        // Buat instance object realm baru
        realm = Realm.getInstance(getActivity());
        // Create object adapter
        adapter = new rowGuestList(getActivity(), namaBirthdateGuest);
        // Load API guest dari url, langsung pasang di adapter
        loadPeople();
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Buat instance object realm baru
        realm = Realm.getInstance(getActivity());
        // Create object adapter
        adapter = new rowGuestList(getActivity(), namaBirthdateGuest);
        // Load API guest dari url, langsung pasang di adapter
        loadPeople();

        return super.onCreateView(inflater, container, savedInstanceState);
    } */

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // EVENT GRIDVIEW
        // Buat instance object realm baru
        realm = Realm.getInstance(getActivity());
        // Load API dari url
        loadPeople();
    } */

    @ItemClick(R.id.listGuest)
    void guestListItemClicked (dataStructureGuest guest) {
        try {
            ((onGuestItemListClickListener) activity).onGuestItemListClick(guest);
        } catch (ClassCastException activityClass) {
            throw new ClassCastException(activity.toString()
                    + " must implement onGuestItemListClickListener");
        }
        onDestroy();
    }

    private void loadPeople() {
        // Load data gridview dari object realm lokal sebelum fetch API dari url
        List<PersonGuestModel> realmLocalPeople = realm.where(PersonGuestModel.class).findAll();
        // Set data lokal pada adapter gridview
        for (PersonGuestModel person : realmLocalPeople) {
            Log.d("realMadrid", person.toString());
            namaBirthdateGuest.add(new dataStructureGuest(person.getName(),person.getBirthdate()));
        }
        listGuest.setAdapter(adapter);
        listGuest.invalidate();

        // Buat exclusion strategies kelas gson untuk memindahkan hasil parsing
        // http response ke object realm yang akan digunakan nanti
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();
        // Lakukan pemanggilan REST API melalui restAdapter untuk mengupdate data people lokal Realm
        restAdapter = new RestAdapter.Builder().setEndpoint(URL).setConverter(new GsonConverter(gson)).build();
        peopleInterfaceAPI peopleAPI = restAdapter.create(peopleInterfaceAPI.class);

        peopleAPI.getPeople(new Callback<List<PersonGuestModel>>() {
            @Override
            public void success(List<PersonGuestModel> personGuestModels, Response response) {
                // Update data lokal menggunakan realmCopyToOrUpdate
                realm.beginTransaction();
                List<PersonGuestModel> realmOnlinePeople = realm.copyToRealmOrUpdate(personGuestModels);
                realm.commitTransaction();

                // Hapus adapter lama dari data people Realm Lokal
                adapter.clear();
                // Baca data dari hasil query Result realmObject, masukkan ke adapter
                for (PersonGuestModel person : realmOnlinePeople) {
                    namaBirthdateGuest.add(new dataStructureGuest(person.getName(),person.getBirthdate()));
                }
                listGuest.setAdapter(adapter);
                listGuest.invalidate();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clear();
       // realm.close(); // tutup instans realm ketika activity closed
    }
/*
    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("namaGuest", namePeopleSelected);
        intent.putExtra("birthdayGuest", birthdayPeopleSelected);
        setResult(RESULT_OK, intent);
        adapter.clear();
        super.finish();
    }
    */
}
