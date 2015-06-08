package com.example.steve.tessuitmediaandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@EActivity(R.layout.activity_event)
@OptionsMenu(R.menu.menu_event)

public class EventActivity extends ActionBarActivity {
    rowEventList adapter;
    @ViewById ListView listEvent;
    @ViewById Toolbar eventToolbar;
    private ArrayList<dataStructureEvent> namaTanggalEvent;
    private String nameEventSelected = "kosong";

    @AfterViews
    void setListViewAndToolbar() {
        // EVENT LISTVIEW

        // Pasang list di atas dengan kelas AdapterArray rowEventList
        namaTanggalEvent = new ArrayList<dataStructureEvent>();
        adapter = new rowEventList(this, namaTanggalEvent);
        listEvent.setAdapter(adapter);

        // Tambahkan list event pada adapter yang sudah dibind dengan list view
        adapter.add(new dataStructureEvent("acara ulang tahun", "20 Maret 2015"));
        adapter.add(new dataStructureEvent("acara olahraga", "21 Maret 2015"));
        adapter.add(new dataStructureEvent("acara reuni sekolah", "22 Maret 2015"));
        adapter.add(new dataStructureEvent("acara rapat", "23 Maret 2015"));
        adapter.add(new dataStructureEvent("acara rekreasi", "24 Maret 2015"));

        // TOOLBAR

        // Setting action bar sebagai toolbar (diintegrasikan)
        setSupportActionBar(eventToolbar);

        // Setting elemen toolbar (back icon dan title)
        eventToolbar.setNavigationIcon(R.drawable.btn_back_article_normal);

        // Pasang event click listener pada ikon toolbar
        eventToolbar.setNavigationOnClickListener(new View.OnClickListener() {          // Back artikel button
            @Override
            public void onClick(View v) {
                eventToolbar.setNavigationIcon(R.drawable.btn_back_article_selected);
            }
        });
    }

    @OptionsItem(R.id.searchButton)
    void searchItemSelected() {
        // Menu search pada toolbar diklik
    }

    @OptionsItem(R.id.newArticleButton1)
    void newArticleItemSelected(MenuItem item) {
        // Menu add new article pada toolbar diklik
        item.setIcon(R.drawable.btn_new_media_article_selected);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Setting ikon dan menu toolbar ke semula
        eventToolbar.setNavigationIcon(R.drawable.btn_back_article_normal);
    }

    @ItemClick(R.id.listEvent)
    void eventListItemClicked(dataStructureEvent event) {
        // Salah satu item pada event listview diklik
        nameEventSelected = event.getNama();
        finish();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("namaEvent", nameEventSelected);
        setResult(RESULT_OK, intent);
        adapter.clear();
        super.finish();
    }
}
