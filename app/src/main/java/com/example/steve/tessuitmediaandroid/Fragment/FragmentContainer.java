package com.example.steve.tessuitmediaandroid.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.example.steve.tessuitmediaandroid.EnterNameActivity;
import com.example.steve.tessuitmediaandroid.EnterNameActivity_;
import com.example.steve.tessuitmediaandroid.EventActivity;
import com.example.steve.tessuitmediaandroid.EventActivity_;
import com.example.steve.tessuitmediaandroid.GuestActivity;
import com.example.steve.tessuitmediaandroid.GuestActivity_;
import com.example.steve.tessuitmediaandroid.MainMenuActivity;
import com.example.steve.tessuitmediaandroid.MainMenuActivity_;
import com.example.steve.tessuitmediaandroid.R;
import com.example.steve.tessuitmediaandroid.dataStructureEvent;
import com.example.steve.tessuitmediaandroid.dataStructureGuest;

public class FragmentContainer extends ActionBarActivity implements EnterNameActivity.onSignUpButtonClickListener,
        MainMenuActivity.onEventButtonClickListener, MainMenuActivity.onGuestButtonClickListener,
        EventActivity.onEventItemListClickListener, GuestActivity.onGuestItemListClickListener {

   /* private static final int REQUEST_CODE_EVENT = 1;
    private static final int REQUEST_CODE_GUEST = 2; */

    private String nama = "";
    private String namaEvent = "";
    private String namaGuest = "";
    private String birthdateGuest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Buat fragment pertama yang muncul
            EnterNameActivity firstFragment = new EnterNameActivity_();
            // Tambahkan fragment ke frame layout 'fragment_container'
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onSignUpButtonClick(String nama) {
        // Buat fragment main menu, tambahkan argument yang dipassing ke fragment tujuan
        MainMenuActivity mainMenuFragment = new MainMenuActivity_();
        Bundle argsFromEnterName = new Bundle();
        argsFromEnterName.putString("enterNama",nama);
        mainMenuFragment.setArguments(argsFromEnterName);
        // Simpan nama ke Activity FragmentContainer ini
        this.nama = nama;
        // Ganti fragment saat ini dengan mainMenuFragment menggunakan FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onEventButtonClick() {
        // Buat fragment Event List Fragment
        EventActivity eventFragment = new EventActivity_();
        // Ganti fragment saat ini dengan EventFragment menggunakan FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, eventFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onGuestButtonClick() {
        // Buat fragment Guest List Fragment
        GuestActivity guestFragment = new GuestActivity_();
        // Ganti fragment saat ini dengan GuestFragment menggunakan FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, guestFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onEventItemListClick(dataStructureEvent event) {
        // Buat fragment Main Menu Fragment, sisipkan argumen di dalam untuk passing message
        MainMenuActivity mainMenuFragment = new MainMenuActivity_();
        Bundle argsFromEvent = new Bundle();
        argsFromEvent.putString("namaEvent",event.getNama());
        if ((namaGuest != null) && (birthdateGuest != null)) {
            argsFromEvent.putString("namaGuest",namaGuest);
            argsFromEvent.putString("birthdateGuest",birthdateGuest);
        }
        if (nama != null) {
            argsFromEvent.putString("enterNama",nama);
        }
        mainMenuFragment.setArguments(argsFromEvent);
        // Simpan nama event ke Activity FragmentContainer ini
        namaEvent = event.getNama();
        // Ganti fragment ke MainMenu Fragment, kirim event ke Main Menu
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.commit();
    }

    @Override
    public void onGuestItemListClick(dataStructureGuest guest) {
        // Buat fragment Main Menu Fragment, sisipkan argument di dalam untuk passing message
        MainMenuActivity mainMenuFragment = new MainMenuActivity_();
        Bundle argsFromEvent = new Bundle();
        argsFromEvent.putString("namaGuest", guest.getNama());
        argsFromEvent.putString("birthdateGuest", guest.getBirthday());
        if (namaEvent != null) {
            argsFromEvent.putString("namaEvent",namaEvent);
        }
        if (nama != null) {
            argsFromEvent.putString("enterNama",nama);
        }
        mainMenuFragment.setArguments(argsFromEvent);
        // Simpan nama guest ke Activity FragmentContainer ini
        namaGuest = guest.getNama();
        birthdateGuest = guest.getBirthday();
        // Ganti fragment ke MainMenu Fragment, kirim guest ke Main Menu
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.commit();
    }
}
