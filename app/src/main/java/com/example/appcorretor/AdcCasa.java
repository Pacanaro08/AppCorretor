package com.example.appcorretor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdcCasa extends AppCompatActivity implements View.OnClickListener {

    private EditText idDono, nomeDono, telefoneDono, enderecoCasa, valorCasa;
    private ImageView imageView;
    private Button btnAdicionar, btnExcluir;
    private final DbData dbData = new DbData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_casa);

        idDono = (EditText)findViewById(R.id.idDono);
        nomeDono = (EditText)findViewById(R.id.nomeDono);
        telefoneDono = (EditText)findViewById(R.id.telefoneDono);
        enderecoCasa = (EditText)findViewById(R.id.enderecoCasa);
        valorCasa = (EditText)findViewById(R.id.valorCasa);
        btnAdicionar = (Button)findViewById(R.id.btnAdicionar);
        btnExcluir = (Button)findViewById(R.id.btnExcluir);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnAdicionar.setOnClickListener(this);
        btnExcluir.setOnClickListener(this);
        imageView.setOnClickListener(this);

        imageView.setEnabled(false);

        if(getIntent().getExtras() != null){
            setTitle(getString(R.string.titulo_editando));
            int codigo = getIntent().getExtras().getInt("consulta");
            dbData.carregaDados(codigo);
            imageView.setEnabled(true);

            idDono.setText(String.valueOf(dbData.getIdDono()));
            nomeDono.setText(String.valueOf(dbData.getNome()));
            telefoneDono.setText(String.valueOf(dbData.getTelefone()));
            enderecoCasa.setText(String.valueOf(dbData.getEndereco()));
            valorCasa.setText(String.valueOf(dbData.getValor()));

        }else{
            setTitle(getString(R.string.titulo_inclusao));
        }
        btnExcluir.setEnabled(true);
        if(dbData.getIdDono() == -1){
            btnExcluir.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdicionar: {
                boolean valido = true;
                dbData.setNome(nomeDono.getText().toString());
                dbData.setTelefone(telefoneDono.getText().toString().trim());
                dbData.setEndereco(enderecoCasa.getText().toString());
                dbData.setValor(valorCasa.getText().toString().trim());

                if(dbData.getNome().equals("")){
                    nomeDono.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if(dbData.getTelefone().equals("")){
                    telefoneDono.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if(dbData.getEndereco().equals("")){
                    enderecoCasa.setError(getString(R.string.obrigatorio));
                    valido = false;
                }
                if(dbData.getValor().equals("")){
                    valorCasa.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if(valido){
                    dbData.salvar();
                    finish();
                }
                break;
            }
            case R.id.btnExcluir:{
                dbData.excluir();
                finish();
                break;
            }
            case R.id.imageView:{
                if(getIntent().getExtras() != null) {
                    int idDono1 = dbData.getIdDono();
                    Intent intent = new Intent(this, AdcImagem.class);
                    intent.putExtra("adcImagem", idDono1);
                    startActivity(intent);
                    break;
                }
            }
        }

    }
}