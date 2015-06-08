package com.example.steve.tessuitmediaandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_enter_name)

public class EnterNameActivity extends Activity {
    @ViewById EditText nameInput;
    @ViewById Button signUpButton;

    @Click
    void signUpButton() {
        // Set background button ketika diklik
        signUpButton.setBackgroundResource(R.drawable.btn_signup_selected);
        // Pindah ke activity main menu
        Intent intent = new Intent(this, MainMenuActivity_.class);
        intent.putExtra("nama", nameInput.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset background button
        signUpButton.setBackgroundResource(R.drawable.btn_signup_normal);
    }
}
