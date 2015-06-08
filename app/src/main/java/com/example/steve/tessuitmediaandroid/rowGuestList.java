package com.example.steve.tessuitmediaandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class rowGuestList extends ArrayAdapter<dataStructureGuest> {
    private final Context context;
    private final ArrayList<dataStructureGuest> listGuest;

    public rowGuestList(Context context, ArrayList<dataStructureGuest> listGuest) {
        super(context, R.layout.activity_row_guest_list, listGuest);
        this.context = context;
        this.listGuest = listGuest;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Bind row guest view dengan inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowGuestView = inflater.inflate(R.layout.activity_row_guest_list, parent, false);

        // Bind komponen widget row view layout activity
        ImageView imageGuestView = (ImageView) rowGuestView.findViewById(R.id.guest_view);
        TextView nameGuestView = (TextView) rowGuestView.findViewById(R.id.nama_guest);

        // Setting komponen widget pembentuk row guest
        imageGuestView.setImageResource(R.drawable.ikon_tamu);
        nameGuestView.setText(listGuest.get(position).getNama());

        return rowGuestView;
    }
}
