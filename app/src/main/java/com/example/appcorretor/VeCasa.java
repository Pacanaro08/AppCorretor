package com.example.appcorretor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class VeCasa extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listViewDados;
    private DataAdapter dataAdapter;
    private ArrayList<DbData> dbData;
    private DbData dbDataEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ve_casa);

        listViewDados = (ListView) findViewById(R.id.listaDados);
        listViewDados.setOnItemClickListener(this);

        dbData = new DbData(this).getDbData();
        dataAdapter = new DataAdapter(this, dbData);
        listViewDados.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DbData dbData1 = dbData.get(position);
        Intent intent = new Intent(this, AdcCasa.class);
        intent.putExtra("consulta", dbData1.getIdDono());
        dbDataEdicao = dbData1;
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dbDataEdicao != null){
            dbDataEdicao.carregaDados(dbDataEdicao.getIdDono());
            finish();
            dataAdapter.notifyDataSetChanged();
        }
    }
}