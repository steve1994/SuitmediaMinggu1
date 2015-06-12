package com.example.steve.tessuitmediaandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.steve.tessuitmediaandroid.Fragment.FragmentContainer;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.activity_main_menu)
public class MainMenuActivity extends Fragment {
    @ViewById Button pilihEventButton;
    @ViewById Button pilihGuestButton;
    @ViewById TextView nameView;
    @FragmentArg("enterNama") String nama;
    @FragmentArg("namaEvent") String namaEvent;
    @FragmentArg("namaGuest") String namaGuest;
    @FragmentArg("birthdateGuest") String birthdateGuest;
    private Activity activity; // activity fragment container
    private static final int REQUEST_CODE_EVENT = 1;
    private static final int REQUEST_CODE_GUEST = 2;

    // INTERFACE UNTUK BERKOMUNIKASI DENGAN FRAGMENT CONTAINER
    public interface onEventButtonClickListener {
        public void onEventButtonClick();
    }
    public interface onGuestButtonClickListener {
        public void onGuestButtonClick();
    }

    @AfterViews
    public void setNamaAndButton() {
        // Setting text pada TextView 'nama' dan Button 'pilih event' dan 'pilih guest'
        if (nama != null) {
            nameView.setText(nama);
            showNameCharacteristics();
        }
        if (namaEvent == null) {
            pilihEventButton.setText("pilih event");
        } else {
            pilihEventButton.setText(namaEvent);
        }
        if ((namaGuest == null) && (birthdateGuest == null)) {
            pilihGuestButton.setText("pilih guest");
        } else {
            pilihGuestButton.setText(namaGuest);

            // Parsing birthday guest (dapat bulan dan tanggal/hari)
            Log.d("barcelona", birthdateGuest);
            String[] parseBirthdayGuest = birthdateGuest.split("[-]");
            int dayBirthdayGuest = Integer.parseInt(parseBirthdayGuest[2]);         // Dapat hari dari birthday guest
            int monthBirthdayGuest = Integer.parseInt(parseBirthdayGuest[1]);       // Dapat bulan dari birthday guest

            // Klasifikasi kategori dari elemen hari Birthday Guest
            String kategoriDay;
            if ((dayBirthdayGuest % 2 == 0) && (dayBirthdayGuest % 3 == 0)) {
                kategoriDay = "iOS";
            } else {
                if (dayBirthdayGuest % 2 == 0) {
                    kategoriDay = "blackberry";
                } else if (dayBirthdayGuest % 3 == 0) {
                    kategoriDay = "android";
                } else {
                    kategoriDay = "feature phone";
                }
            }
            // Klasifikasi kategori dari elemen bulan Birthday Guest
            String kategoriMonth;
            if (isMonthPrime(monthBirthdayGuest)) {
                kategoriMonth = "prima";
            } else {
                kategoriMonth = "bukan prima";
            }

            // Output hasil klasifikasi elemen hari dan bulan Birthday Guest
            String toastMessage = kategoriDay + " dan " + kategoriMonth;
            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Click
    public void pilihEventButton() {
        try {
            ((onEventButtonClickListener) activity).onEventButtonClick();
        } catch (ClassCastException activityClass) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEventButtonClickListener");
        }
    }

    @Click
    public void pilihGuestButton() {
        try {
            ((onGuestButtonClickListener) activity).onGuestButtonClick();
        } catch (ClassCastException activityClass) {
            throw new ClassCastException(activity.toString()
                    + " must implement onGuestButtonClickListener");
        }
    }

    public void showNameCharacteristics() {
        // Cek nama apakah palindrom atau bukan
        if (isNamaPalindrom(nama)) {
            Toast.makeText(getActivity(),"Nama Anda berbentuk palindrom",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"Nama Anda bukan berbentuk palindrom",Toast.LENGTH_SHORT).show();
        }
    }

   /* @AfterExtras
    public void showNameCharacteristics() {
        // Cek nama apakah palindrom atau bukan
        if (isNamaPalindrom(nama)) {
            Toast.makeText(getBaseContext(),"Nama Anda berbentuk palindrom",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(),"Nama Anda bukan berbentuk palindrom",Toast.LENGTH_SHORT).show();
        }
    }

    @Click
    public void pilihEventButton() {
        Intent intent = new Intent(this, EventActivity_.class);
        startActivityForResult(intent, REQUEST_CODE_EVENT);
    }

    @Click
    public void pilihGuestButton() {
        Intent intent = new Intent(this, GuestActivity_.class);
        startActivityForResult(intent, REQUEST_CODE_GUEST);
    }

    @Trace
    @OnActivityResult(REQUEST_CODE_EVENT)
    void onResultEvent(int resultCode, Intent data, @OnActivityResult.Extra("namaEvent") String namaEvent) {
        if (resultCode == RESULT_OK) {
            pilihEventButton.setText(namaEvent);
        }
    }

    @Trace
    @OnActivityResult(REQUEST_CODE_GUEST)
    void onResultGuest(int resultCode, Intent data,@OnActivityResult.Extra("namaGuest") String namaGuest,
                       @OnActivityResult.Extra("birthdayGuest") String birthdayGuest) {
        if (resultCode == RESULT_OK) {
            // Set nama guest ke pilihGuestButton
            pilihGuestButton.setText(namaGuest);

            // Parsing birthday guest (dapat bulan dan tanggal/hari)
            String[] parseBirthdayGuest = birthdayGuest.split("[-]");
            int dayBirthdayGuest = Integer.parseInt(parseBirthdayGuest[2]);         // Dapat hari dari birthday guest
            int monthBirthdayGuest = Integer.parseInt(parseBirthdayGuest[1]);       // Dapat bulan dari birthday guest

            // Klasifikasi kategori dari elemen hari Birthday Guest
            String kategoriDay;
            if ((dayBirthdayGuest % 2 == 0) && (dayBirthdayGuest % 3 == 0)) {
                kategoriDay = "iOS";
            } else {
                if (dayBirthdayGuest % 2 == 0) {
                    kategoriDay = "blackberry";
                } else if (dayBirthdayGuest % 3 == 0) {
                    kategoriDay = "android";
                } else {
                    kategoriDay = "feature phone";
                }
            }
            // Klasifikasi kategori dari elemen bulan Birthday Guest
            String kategoriMonth;
            if (isMonthPrime(monthBirthdayGuest)) {
                kategoriMonth = "prima";
            } else {
                kategoriMonth = "bukan prima";
            }
            // Output hasil klasifikasi elemen hari dan bulan Birthday Guest
            String toastMessage = kategoriDay + " dan " + kategoriMonth;
            Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_LONG).show();
        }
    } */

    // PRIMITIF BOOLEAN

    private boolean isNamaPalindrom (String nama) {
        int indexDepan = 0;
        int indexBelakang = nama.length() - 1;
        boolean palindrom = true;

        do {
            if (nama.charAt(indexDepan) != nama.charAt(indexBelakang)) {
                palindrom = false;
            }
            indexDepan++;
            indexBelakang--;
        } while (indexDepan <= indexBelakang);

        return palindrom;
    }

    private boolean isMonthPrime(int month) {
        boolean Prime;
        switch (month) {
            case 2 :
            case 3 :
            case 5 :
            case 7 :
            case 11 :
                Prime = true; break;
            default :
                Prime = false; break;
        }
        return Prime;
    }
}
