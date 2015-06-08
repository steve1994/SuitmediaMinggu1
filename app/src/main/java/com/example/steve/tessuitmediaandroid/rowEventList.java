package com.example.steve.tessuitmediaandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class rowEventList extends ArrayAdapter<dataStructureEvent> {
    private final Context context;
    private final ArrayList<dataStructureEvent> namaTanggalEvent;

    public rowEventList(Context context, ArrayList<dataStructureEvent> namaTanggalEvent) {
        super(context, R.layout.activity_row_event_list, namaTanggalEvent);
        this.context = context;
        this.namaTanggalEvent = namaTanggalEvent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Bind row event view dengan inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowEventView = inflater.inflate(R.layout.activity_row_event_list, parent, false);

        // Bind komponen widget row view layout activity
        TextView namaEventView = (TextView) rowEventView.findViewById(R.id.nama_event);
        TextView tanggalEventView = (TextView) rowEventView.findViewById(R.id.tanggal_event);
        ImageView imageEventView = (ImageView) rowEventView.findViewById(R.id.image_event);

        // Setting widget nama, tanggal, dan gambar pada row view layout activity
        namaEventView.setText(namaTanggalEvent.get(position).getNama());
        tanggalEventView.setText(namaTanggalEvent.get(position).getTanggal());

        if (namaTanggalEvent.get(position).getNama().equals("acara ulang tahun")) {
            imageEventView.setImageResource(R.drawable.acara_ulang_tahun);
        }
        else if (namaTanggalEvent.get(position).getNama().equals("acara rapat")) {
            imageEventView.setImageResource(R.drawable.acara_rapat);
        }
        else if (namaTanggalEvent.get(position).getNama().equals("acara rekreasi")) {
            imageEventView.setImageResource(R.drawable.acara_rekreasi);
        }
        else if (namaTanggalEvent.get(position).getNama().equals("acara reuni sekolah")) {
            imageEventView.setImageResource(R.drawable.acara_reuni_sekolah);
        }
        else {
            imageEventView.setImageResource(R.drawable.acara_olahraga);
        }

        return rowEventView;
    }
}
