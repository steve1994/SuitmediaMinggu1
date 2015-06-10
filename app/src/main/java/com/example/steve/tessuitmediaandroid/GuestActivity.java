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
import android.util.Log;
import android.view.View;
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

@EActivity(R.layout.activity_guest)

public class GuestActivity extends Activity {
    private rowGuestList adapter;
    private Realm realm;
    @ViewById GridView listGuest;
    private ArrayList<dataStructureGuest> namaBirthdateGuest = new ArrayList<>();
    private String namePeopleSelected = "anonim";
    private String birthdayPeopleSelected = "31-12-1999";
    private RestAdapter restAdapter;
    private static final String URL = "http://dry-sierra-6832.herokuapp.com/api";
    private String result;

    // PRIMITIF BOOLEAN
    public boolean isOnline() {
        // Cek apakah device terhubung ke jaringan/internet atau tidak
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @AfterViews
    void setGridView() {
        // Hapus object realm terakhir
        Realm.deleteRealmFile(this);
        // Buat instance object realm baru
        realm = Realm.getInstance(this);
        // Create object adapter
        adapter = new rowGuestList(this, namaBirthdateGuest);
        //fetchPeopleFromAPI("http://dry-sierra-6832.herokuapp.com/api");
    }

    /*@Background
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
    }*/

    @ItemClick(R.id.listGuest)
    void guestListItemClicked (dataStructureGuest guest) {
        namePeopleSelected = guest.getNama();
        birthdayPeopleSelected = guest.getBirthday();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Variabel untuk menampung list object Realm : PersonGuestModel
        List<PersonGuestModel> people = null;

        // Cek apakah device online atau tidak
        if (isOnline()) {
            // Load dari api menggunakan retrofit mengembalikan list people berbentuk realm object
            people = loadPeople();

            // Fetch row model Realm : PersonGuestModel.
            List<PersonGuestModel> result = realm.where(PersonGuestModel.class).findAll();

            // Cek apakah hasil eksekusi query Realm di atas kosong
            if (result.size() == 0) {
                // Set value list people ke Realm model
                for (PersonGuestModel person : people) {
                    realm.beginTransaction();
                    PersonGuestModel ppl = realm.createObject(PersonGuestModel.class);
                    ppl.setName(person.getName());
                    ppl.setBirthdate(person.getBirthdate());
                    realm.commitTransaction();
                }
            } else {
                // Update data dari people list yang sudah diload sebelumnya
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(people);
                realm.commitTransaction();
            }
        }

        // Fetch row model Realm : PersonGuestModel. Mengantisipasi jika ada update
        List<PersonGuestModel> resultIfUpdate = realm.where(PersonGuestModel.class).findAll();

        // Baca data dari hasil query Result realmObject, masukkan ke adapter
        for (PersonGuestModel person : resultIfUpdate) {
            adapter.add(new dataStructureGuest(person.getName(),person.getBirthdate()));
        }
        // Pasang adapter pada gridview
        listGuest.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listGuest.invalidate();
    }

    private List<PersonGuestModel> loadPeople() {
        // Lakukan pemanggilan REST API melalui restAdapter
        restAdapter = new RestAdapter.Builder().setEndpoint(URL).build();
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
                result = sb.toString(); // string http response json api
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("SUITMEDIA", "gagal " + retrofitError.getMessage());
            }
        });

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

        // Masukkan ke array of list of object Realm PersonGuestModel
        List<PersonGuestModel> people = gson.fromJson(result, new TypeToken<List<PersonGuestModel>>() {}.getType());

        // Buka transaksi realm object, kopi list people hasil parsing gson ke object yang dimanage realm
        realm.beginTransaction();
        Collection<PersonGuestModel> realmPeople = realm.copyToRealm(people);
        realm.commitTransaction();

        Log.d("realMadrid",realmPeople.toString());

        // kembalikan list people yang sudah berbentuk object Realm
        return new ArrayList<>(realmPeople);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close(); // tutup instans realm ketika activity closed
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("namaGuest", namePeopleSelected);
        intent.putExtra("birthdayGuest", birthdayPeopleSelected);
        setResult(RESULT_OK, intent);
        adapter.clear();
        super.finish();
    }
}
