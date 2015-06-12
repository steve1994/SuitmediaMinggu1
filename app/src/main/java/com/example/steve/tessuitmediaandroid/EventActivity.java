package com.example.steve.tessuitmediaandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@EFragment(R.layout.activity_event)

public class EventActivity extends Fragment {
    private rowEventList adapter;
    @ViewById ListView listEvent;
    @ViewById Toolbar eventToolbar;
    private ArrayList<dataStructureEvent> namaTanggalEvent;
    private String nameEventSelected = "kosong";
    private Activity activity;

    public interface onEventItemListClickListener {
        public void onEventItemListClick(dataStructureEvent event);
    }

    @AfterViews
    void setListViewAndToolbar() {
        // Masukkan data adapter pada arraylist, pasang di ListAdapter Fragment
        namaTanggalEvent = new ArrayList<>();
        namaTanggalEvent.add(new dataStructureEvent("acara ulang tahun", "20 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara olahraga", "21 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara reuni sekolah", "22 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara rapat", "23 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara rekreasi", "24 Maret 2015"));
        // Buat adapter, setting di event list fragment
        adapter = new rowEventList(getActivity(), namaTanggalEvent);
        listEvent.setAdapter(adapter);

        // TOOLBAR

       /* // Setting action bar sebagai toolbar (diintegrasikan)
        setSupportActionBar(eventToolbar);

        // Setting elemen toolbar (back icon dan title)
        eventToolbar.setNavigationIcon(R.drawable.btn_back_article_normal);

        // Pasang event click listener pada ikon toolbar
        eventToolbar.setNavigationOnClickListener(new View.OnClickListener() {          // Back artikel button
            @Override
            public void onClick(View v) {
                eventToolbar.setNavigationIcon(R.drawable.btn_back_article_selected);
            }
        }); */
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // EVENT LISTVIEW

        // Tambahkan list event pada adapter yang sudah dibind dengan list view
        namaTanggalEvent = new ArrayList<>();
        adapter = new rowEventList(getActivity(), namaTanggalEvent);
        adapter.add(new dataStructureEvent("acara ulang tahun", "20 Maret 2015"));
        adapter.add(new dataStructureEvent("acara olahraga", "21 Maret 2015"));
        adapter.add(new dataStructureEvent("acara reuni sekolah", "22 Maret 2015"));
        adapter.add(new dataStructureEvent("acara rapat", "23 Maret 2015"));
        adapter.add(new dataStructureEvent("acara rekreasi", "24 Maret 2015"));
        listEvent.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Masukkan data adapter pada arraylist, pasang di ListAdapter Fragment
        namaTanggalEvent = new ArrayList<>();
        namaTanggalEvent.add(new dataStructureEvent("acara ulang tahun", "20 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara olahraga", "21 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara reuni sekolah", "22 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara rapat", "23 Maret 2015"));
        namaTanggalEvent.add(new dataStructureEvent("acara rekreasi", "24 Maret 2015"));
        // Buat adapter, setting di event list fragment
        adapter = new rowEventList(getActivity(), namaTanggalEvent);
        listEvent.setAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    } */

    @OptionsItem(R.id.searchButton)
    void searchItemSelected() {
        // Menu search pada toolbar diklik
    }

    @OptionsItem(R.id.newArticleButton1)
    void newArticleItemSelected(MenuItem item) {
        // Menu add new article pada toolbar diklik
        item.setIcon(R.drawable.btn_new_media_article_selected);
    }

  /*  @ItemClick(R.id.listEvent)
    public void onEventItemClicked(dataStructureEvent event) {
        try {
            ((onEventItemListClickListener) activity).onEventItemListClick(event);// Buat fragment Main Menu Fragment
            Log.d("barcelona","EVENT CLICK TRIGGERED");
        } catch (ClassCastException activityClass) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEventItemListClickListener");
        }
    } */

  /*  @Override
    public void onResume() {
        super.onResume();
        // Setting ikon dan menu toolbar ke semula
        eventToolbar.setNavigationIcon(R.drawable.btn_back_article_normal);
    } */

   /* @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v,position,id);

        try {
            ((onEventItemListClickListener) activity).onEventItemListClick(namaTanggalEvent.get(position));
        } catch (ClassCastException activityClass) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEventItemListClickListener");
        }
        onDestroy();
    } */

    @ItemClick(R.id.listEvent)
    public void onEventItemClicked (dataStructureEvent event) {
        try {
            ((onEventItemListClickListener) activity).onEventItemListClick(event);
        } catch (ClassCastException activityClass) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEventItemListClickListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clear();
    }
}
