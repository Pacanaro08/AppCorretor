package com.example.appcorretor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends ArrayAdapter<DbData> {

    private final ArrayList<DbData> dbData;

    public DataAdapter(@NonNull Context context, @NonNull ArrayList<DbData> dbData) {
        super(context, 0, dbData);
        this.dbData = dbData;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DbData dbData1 = dbData.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista,null);

        TextView txtId = convertView.findViewById(R.id.txtId);
        TextView txtNome = convertView.findViewById(R.id.txtNome);
        TextView txtEndereco = convertView.findViewById(R.id.txtEndereco);

        txtId.setText(String.valueOf(dbData1.getIdDono()));
        txtNome.setText(String.valueOf(dbData1.getNome()));
        txtEndereco.setText(String.valueOf(dbData1.getEndereco()));

        return convertView;
    }
}
